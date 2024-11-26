package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    val restaurant = remember { mutableStateOf<Restaurant?>(null) }
    val isLoading = remember { mutableStateOf(true) }
//TODO - add loading

    LaunchedEffect(Unit) {
        isLoading.value = true
        restaurant.value =
            restaurantsService.get("ChIJFVWAmflYwokRdnX3IwqbR20")
        Log.v("Value changed to ", restaurant.value?.id?.let {it} ?: "Null")
        isLoading.value = false
    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }


    Box() {
        if(!isLoading.value) {
                if (restaurant.value != null) {
                    Column() {
                        //val currentRestaurant : Restaurant = restaurant.value?:Restaurant("","",0,0,"")
                        Text(restaurant.value?.id?.let { it } ?: "None")


                        Text(restaurant.value?.name?.let { it } ?: "None")
                        Text(restaurant.value?.address?.let { it } ?: "None")
                        Text(restaurant.value?.rating?.let { it.toString() } ?: "None")
                        Text(restaurant.value?.ratingsNumber?.let { it.toString() } ?: "None")
                    }
                }

        }

    }

}