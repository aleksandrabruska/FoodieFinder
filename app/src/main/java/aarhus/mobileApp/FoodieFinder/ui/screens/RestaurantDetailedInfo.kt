package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.googlePlaces.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.BasicRestaurantInfoBox
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.DetailedRestaurantInfoBox
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.RestaurantPhoto
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun RestaurantDetailedInfo(id: String) {
    val restaurantsService = remember { KtorRestaurantsService() }
    val isLoading = remember { mutableStateOf(true) }
    val restaurant = remember { mutableStateOf<Restaurant?>(null) }
    val controller = rememberNavController()
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        isLoading.value = true
        restaurant.value =
            restaurantsService.get(id)
        isLoading.value = false
    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }

    if(!isLoading.value) {
        OutlinedCard(
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Black),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                .fillMaxWidth()
                .scrollable(orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }).verticalScroll(scrollState, offset < -40) ) {
                restaurant.value?.let { r ->
                    Text(r.name?.let { it } ?: "None",
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp))
                    BasicRestaurantInfoBox(r)
                    RestaurantPhoto(r.photoReference)
                    DetailedRestaurantInfoBox(r)
                }

            }
        }
    }
    else{
        Loader()
    }
}