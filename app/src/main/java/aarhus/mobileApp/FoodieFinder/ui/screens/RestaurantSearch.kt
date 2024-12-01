package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.internal.composableLambdaN
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


//TODO - add loading

@Composable
fun RestaurantSearch(){
    val restaurantsService = remember { KtorRestaurantsService() }
    val isLoading = remember { mutableStateOf(true) }
    val restaurants = remember { mutableStateOf<List<Restaurant>>(emptyList()) }
    val controller = rememberNavController()
    LaunchedEffect(Unit) {
        isLoading.value = true
        restaurants.value =
            restaurantsService.get(40.76240279999999, -73.9780748, 10, 1000)
        isLoading.value = false
    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }

    if(!isLoading.value) {
        for (item in restaurants.value)
            Log.v("resaturant is:", item.name ?: "None")


        NavHost(
            navController = controller,
            startDestination = "venueNo1"
        ) {
            composable("venueNo1") {
                RestaurantInfo(restaurants.value.get(0),
                    {controller.navigate("venueNo1details")}) {
                    controller.navigate("venueNo2")
                }
            }
            composable("venueNo1details"){
                RestaurantDetailedInfo(restaurants.value.get(0).id!!)
            }
            composable("venueNo2") {
                RestaurantInfo(restaurants.value.get(1),
                    {controller.navigate("venueNo2details")}) {
                    controller.navigate("venueNo3")
                }
            }
            composable("venueNo2details"){
                RestaurantDetailedInfo(restaurants.value.get(1).id!!)
            }
            composable("venueNo3") {
                RestaurantInfo(restaurants.value.get(2),
                    {controller.navigate("venueNo3details")}) {}
            }
            composable("venueNo3details"){
                RestaurantDetailedInfo(restaurants.value.get(2).id!!)
            }


        }
    }
    else{
        Loader()
    }
}