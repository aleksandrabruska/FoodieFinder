package aarhus.mobileApp.FoodieFinder.integration

import aarhus.mobileApp.FoodieFinder.BuildConfig
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
        private const val PHOTOS_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo"
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

    override suspend fun get(latitude: String, longitude: String): List<Restaurant> {
        val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
        val queryParams = mapOf(
            "location" to latitude+","+longitude,
            "radius" to "1000",
            "type" to "restaurant",
            "key" to apiKey
        )
        return try {
            client.get(BASE_URL) {
                url {
                    queryParams.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
            }.body()
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
            Log.v("Hi", "hi")

            Log.v("Prices", result.getInt("price_level").toString())
            Log.v("Website", result.getString("website"))
            Log.v("Open:", result.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(0))

            val opened = Array<String>(7, {""})
            val openedInfo = result.getJSONObject("opening_hours").getJSONArray("weekday_text")
            for(i in 0..max(openedInfo.length(),opened.size)-1){
                opened[i] = openedInfo.getString(i)
            }
            val photos: JSONArray = result.getJSONArray("photos")
            //Log.v("photos?", result.getJSONArray("photos").toString())
            Log.v("photo count: ", photos.length().toString())
            Log.v("photo", photos[0].toString())
            val photo: JSONObject = photos.getJSONObject(0)
            val reference = photo.getString("photo_reference")
            Log.v("reference", reference)


            val jsonParser = Json {
                ignoreUnknownKeys = true // Default behavior: ignore extra keys
            }
            val restaurant: Restaurant = jsonParser.decodeFromString(result.toString())
            restaurant.openingHours = opened
            restaurant.photoReference = reference
            restaurant


        } catch (e: Exception) {
            Log.v("RESTAURANT SERVICE", e.toString())
            null
        }
    }

    fun close() {
        client.close()
    }

}