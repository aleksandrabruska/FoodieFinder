package aarhus.mobileApp.FoodieFinder.integration

import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant


interface RestaurantsService {
    suspend fun get(latitude: Double, longitude: Double, maxCount: Int, radius: Int): List<Restaurant>
    suspend fun get(id: String): Restaurant?

    //suspend fun getBy(name: String): List<Restaurant>
}