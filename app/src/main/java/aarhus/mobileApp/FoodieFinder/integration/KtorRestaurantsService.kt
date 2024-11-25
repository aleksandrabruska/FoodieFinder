package aarhus.mobileApp.FoodieFinder.integration

import aarhus.mobileApp.FoodieFinder.BuildConfig
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import android.util.Log
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

class KtorRestaurantsService : RestaurantsService {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json"
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
            client.get(BASE_URL) {
                url {
                    queryParams.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
            }.body()
        } catch (e: Exception) {
            Log.v("RESTAURANT SERVICE", e.toString())
            null
        }
    }

    fun close() {
        client.close()
    }

}