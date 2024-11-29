package aarhus.mobileApp.FoodieFinder

//import aarhus.mobileApp.FoodieFinder.MainActivity.Companion.BASE_URL
//import aarhus.mobileApp.FoodieFinder.MainActivity.Companion.BASE_URL_GOOGLE
import aarhus.mobileApp.FoodieFinder.integration.firebase.Users
import aarhus.mobileApp.FoodieFinder.ui.screens.LogIn
import aarhus.mobileApp.FoodieFinder.ui.screens.Register
import aarhus.mobileApp.FoodieFinder.ui.screens.RestaurantDetails
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import aarhus.mobileApp.FoodieFinder.ui.theme.FoodieFinderTheme
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.rememberCoroutineScope
import io.ktor.client.HttpClient
import kotlinx.serialization.SerialName
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import com.google.firebase.firestore.FirebaseFirestore;
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    /*public val apiKey = "${BuildConfig.FOURSQUARE_API_KEY}"
    companion object{
        public const val BASE_URL = "https://api.foursquare.com/v3/places/search"
        public const val BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"

    }*/


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        //Log.v("api", apiKey)
        //runBlocking {
        //    someFunGoogleAPI()
        //}
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()

            scope.launch {
                //val intent = Intent(this@MainActivity, MapsActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                //startActivity(intent)
                val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            }

            FoodieFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                    { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            //Greeting(
                            //    name = "Android",
                            //    modifier = Modifier.padding(innerPadding)
                            //)
                            Register()
                            //Users()
                        }
                }
            }
        }
    }
}

/*
suspend fun someFunGoogleAPI(){
    Log.v ("API", "google places")
    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY // Google Places API key
    val client = HttpClient(CIO)
    val queryParams = mapOf(
        "location" to "40.7629934,-73.9780947", // Latitude and Longitude
        "radius" to "100",                     // Radius in meters (1 km)
        "type" to "restaurant",                 // Type filter for restaurants
        "key" to apiKey                         // API key
    )

    val response: HttpResponse = client.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json") {
        url {
            queryParams.forEach { (key, value) ->
                parameters.append(key, value)
            }
        }
    }

    if (response.status == HttpStatusCode.OK) {
        Log.v("oktag", "ok?")
        // Parse the response
        val responseBody = response.bodyAsText()
        Log.v("API RESPONSE", responseBody)
        val jsonObject = JSONObject(responseBody)
        val results = jsonObject.getJSONArray("results")

        // Print out the restaurant details

        Log.v("length", results.length().toString())
        for (i in 0 until results.length()) {
            val place = results.getJSONObject(i)
            val name = place.getString("name")
            val placeID = place.getString("place_id")
            val location = place.getJSONObject("geometry").getJSONObject("location")
            val latitude = location.getDouble("lat")
            val longitude = location.getDouble("lng")
            val address = place.optString("vicinity", "No address available")
            val rating = place.optDouble("rating", -1.0) // Default to -1.0 if not available
            val userRatingsTotal = place.optInt("user_ratings_total", 0) // Number of ratings

            Log.v("sometag", "Name: $name, Address: $address, Rating: $rating, Ratings Count: $userRatingsTotal, Lat: $latitude, Lng: $longitude")
            Log.v("placefull", "Place ${place.toString()}")
            //getPlaceDetails(client, apiKey, placeID)
        }

    } else {
        Log.v("Failtag", "Request failed with status: ${response.status}")
    }
}

suspend fun getPlaceDetails(client: HttpClient, apiKey: String, placeId: String): String {
    val queryParams = mapOf(
        "place_id" to placeId,  // Pass the place ID
        "fields" to "review",   // Specify that we want the reviews
        "key" to apiKey         // API key
    )

    val response: HttpResponse = client.get("https://maps.googleapis.com/maps/api/place/details/json") {
        url {
            queryParams.forEach { (key, value) ->
                parameters.append(key, value)
            }
        }
    }

    return if (response.status == HttpStatusCode.OK) {
        val responseBody = response.bodyAsText()
        val jsonObject = JSONObject(responseBody)
        val result = jsonObject.optJSONObject("result")
        val reviewsArray = result?.optJSONArray("reviews") ?: JSONArray()

        val reviewsList = mutableListOf<String>()
        for (i in 0 until reviewsArray.length()) {
            val review = reviewsArray.getJSONObject(i)
            val authorName = review.optString("author_name", "Anonymous")
            val comment = review.optString("text", "No comment provided")
            reviewsList.add("Author: $authorName, Comment: $comment")
            Log.v("comment", comment)
        }


        reviewsList.joinToString("\n")
    } else {
        "No reviews available."
    }
}
suspend fun someFun(){
    Log.v("API", "Foursquare API")
    val apiKey = "${BuildConfig.FOURSQUARE_API_KEY}"
    val client = HttpClient(CIO)
    val queryParams = mapOf(
        "ll" to "40.7629934,-73.9780947", // Latitude and Longitude for the location
        "categories" to "13065",        // Category for restaurants
        "radius" to "1000",             // Radius in meters (1 km)
        "limit" to "10"                 // Limit to 10 results
    )

    val response: HttpResponse = client.get(BASE_URL){headers {
        append(HttpHeaders.Authorization, apiKey)
    }
    url {
        queryParams.forEach { (key, value) ->
            parameters.append(key, value)
        }
    }}
    if (response.status == HttpStatusCode.OK) {
            // Parse the response
            val responseBody = response.bodyAsText()
            val jsonObject = JSONObject(responseBody)
            val results = jsonObject.getJSONArray("results")

            // Print out the restaurant details
            for (i in 0 until results.length()) {
                val place = results.getJSONObject(i)
                val name = place.getString("name")
                //val aa = place.getString("")
                val location = place.getJSONObject("location")
                val address = location.optString("formatted_address", "No address available")
                val rating = place.optDouble("rating", -1.0) // Default to -1.0 if not available

                var tipsString = ""
                tipsString = tipsString + "a"
                // Extract tips
                if (place.has("tips")) {
                    val jsonarray = place.getJSONArray("tips")
                    for(i in 0 until jsonarray.length()){
                        val jsonobject = jsonarray.getJSONObject(i)

                        tipsString += "User: ${jsonobject.optString("user", "unknown") }"
                        tipsString += "Tip + ${jsonobject.optString("text", "no text")}"
                    }

                } else {
                    Log.v("notips","No tips available.")
                }
                Log.v("sometag", "Name: $name, Address: $address, Rating: $rating, Tips: $tipsString")
                Log.v("placefull", "Place ${place.toString()}")
            }
        } else {
            Log.v("Failtag","Request failed with status: ${response.status}")
        }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodieFinderTheme {
        Greeting("Android")
    }
}*/