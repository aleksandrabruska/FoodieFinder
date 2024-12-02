package aarhus.mobileApp.FoodieFinder.integration

import aarhus.mobileApp.FoodieFinder.BuildConfig
import aarhus.mobileApp.FoodieFinder.integration.model.Comment
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.Logging

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json


import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.max

class KtorRestaurantsService : RestaurantsService {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json"
        private const val BASE_URL_LIST = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
    //private const val PHOTOS_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo"
    }

    private val client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    //TODO: implement maxCount
    override suspend fun get(latitude: Double, longitude: Double, maxCount: Int, radius: Int): List<Restaurant> {
        val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
        val queryParams = mapOf(
            "location" to latitude.toString()+","+longitude.toString(),
            "radius" to radius.toString(),
            "type" to "restaurant",
            "key" to apiKey
        )
        var restaurants = emptyList<Restaurant>()
        return try {
            val response: HttpResponse = client.get(BASE_URL_LIST) {
                url {
                    queryParams.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
            }
            Log.v("response", response.bodyAsText())
            val responseArray : JSONArray = JSONObject(response.bodyAsText()).getJSONArray("results")
            for (i in 0 until responseArray.length()) {
                val result : JSONObject = responseArray.getJSONObject(i)
                /*val restaurant: Restaurant? = get(result.getString("place_id"))
                if(restaurant != null) {
                    restaurants = restaurants + restaurant!!
                }*/
                Log.v("Before ", "aaa")
                val jsonParser = Json {
                    ignoreUnknownKeys = true // Default behavior: ignore extra keys
                }
                val restaurant: Restaurant = jsonParser.decodeFromString(result.toString())
                Log.v("After ", restaurant.toString())
                val resultFull = deserializeNested(result, restaurant)
                Log.v("After after", restaurant.toString())
                if(resultFull != null) {
                    restaurants = restaurants + resultFull!!
                }

            }
            Log.v("Restaurants found: ", restaurants.size.toString())
            restaurants.subList(0, if(maxCount > restaurants.size) restaurants.size else maxCount )
        } catch (e: Exception) {
            Log.v("RESTAURANT SERVICE", e.toString())
            emptyList()
        }
    }

    override suspend fun get(id: String): Restaurant? {
        val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
        val queryParams = mapOf(
            "place_id" to id,
            "key" to apiKey
        )
        return try {
            val response: HttpResponse = client.get(BASE_URL) {
                url {
                    queryParams.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
            }

            val result : JSONObject = JSONObject(response.bodyAsText()).getJSONObject("result")

            val jsonParser = Json {
                ignoreUnknownKeys = true // Default behavior: ignore extra keys
            }
            val restaurant: Restaurant = jsonParser.decodeFromString(result.toString())
            deserializeNested(result, restaurant)

        } catch (e: Exception) {
            Log.v("RESTAURANT SERVICE", e.toString())
            null
        }
    }

    fun close() {
        client.close()
    }

    fun deserializeNested(result: JSONObject, restaurant: Restaurant) : Restaurant{
        var comments = List<Comment>(0,  {Comment("","")})
        val commentsArray: JSONArray = result.optJSONArray("reviews") ?: JSONArray()
        for (i in 0..commentsArray.length()-1){
            val jsonParser = Json {
                ignoreUnknownKeys = true
            }
            val comment: Comment = jsonParser.decodeFromString(commentsArray.get(i).toString())
            comments = comments + comment
        }

        val opened = Array<String>(7, {""})
        val openedInfo = result.optJSONObject("opening_hours")
        Log.v("resultHours", result.toString())
        if(openedInfo != null){
            val openedInfoText =  openedInfo.optJSONArray("weekday_text")
            if(openedInfoText!= null){
                for(i in 0..max(openedInfoText.length(),opened.size)-1){
                    opened[i] = openedInfoText.getString(i)
                }
                Log.v("hours", opened[0])
                restaurant.openingHours = opened
            }
        }



        val photos: JSONArray? = result.optJSONArray("photos")
        if(photos != null) {
            val photo: JSONObject = photos.getJSONObject(0)
            val reference = photo.getString("photo_reference")
            restaurant.photoReference = reference
        }


        val summary = result.optJSONObject("editorial_summary")?.let{it.getString("overview")}
        val lat = result.optJSONObject("geometry")?.let{it.getJSONObject("location").getDouble("lat")}
        val lng = result.optJSONObject("geometry")?.let{it.getJSONObject("location").getDouble("lng")}

        restaurant.comments = comments
        restaurant.summary = summary
        restaurant.lat = lat
        restaurant.lng = lng
        return restaurant
    }
}