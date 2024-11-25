package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


@Composable
fun RestaurantDetailsBox(id: String) {
    val restaurantsService = remember { KtorRestaurantsService() }
    val restaurant = remember { mutableStateOf<Restaurant>(Restaurant()) }
//TODO - add loading

    LaunchedEffect(key1 = restaurant.value) {
        restaurant.value =
            restaurantsService.get("ChIJFVWAmflYwokRdnX3IwqbR20") ?: Restaurant()
    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }

    Box() {


        //val currentRestaurant : Restaurant = restaurant.value?:Restaurant("","",0,0,"")
        Text(restaurant.value.id)


        Text("name" + restaurant.value.name)
        Text(restaurant.value.address)
        Text(restaurant.value.rating.toString())
        Text(restaurant.value.ratingsNumber.toString())


    }

}