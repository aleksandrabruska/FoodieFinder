package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.MapsActivity
import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.RestaurantDetailsBox
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.RestaurantPhoto
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.launch


@Composable
fun RestaurantDetails() {
    val restaurantsService = remember { KtorRestaurantsService() }
    val restaurant = remember { mutableStateOf<Restaurant?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    //val restaurantPhoto = remember { mutableStateOf<Bitmap?>(null) }
//TODO - add loading

    LaunchedEffect(Unit) {
        isLoading.value = true
        restaurant.value =
            restaurantsService.get("ChIJFVWAmflYwokRdnX3IwqbR20")
        //restaurantPhoto.value =
        //    restaurantsService.getPhoto()
        Log.v("Value changed to ", restaurant.value?.id?.let {it} ?: "Null")
        isLoading.value = false
    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }
    if(!isLoading.value) {
        if (restaurant.value != null) {
            OutlinedCard(
                modifier = Modifier.padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Column(modifier = Modifier.scrollable(orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta ->
                        offset += delta
                        delta
                    }).verticalScroll(scrollState, offset < -40) ){
                    Text(restaurant.value?.name?.let { it } ?: "None",
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center)
                    RestaurantPhoto(restaurant.value?.photoReference)
                    RestaurantDetailsBox(restaurant.value)
                    Button (onClick = {
                        scope.launch {
                            val intent = Intent(context, MapsActivity::class.java)
                            intent.putExtra("restaurant_lat", restaurant.value?.lat)
                            intent.putExtra("restaurant_lng", restaurant.value?.lng)
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            context.startActivity(intent)
                        }
                    }){
                        Text("See on map")
                    }
                }
            }
        }
    }



}