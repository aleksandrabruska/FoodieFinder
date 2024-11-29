package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BasicRestaurantInfoBox(restaurant_: Restaurant) {

    val restaurant = remember { mutableStateOf<Restaurant>(restaurant_) }
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(restaurant.value.address?.let { it } ?: "None", textAlign = TextAlign.Center, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Rating " + (restaurant.value.rating?.let { it.toString() } ?: "None")
                + " based on " + (restaurant.value.ratingsNumber?.let { it.toString() } ?: "None")
                + " reviews", textAlign = TextAlign.Center, fontSize = 18.sp)
        var stars = ""
        for (i in 1..(restaurant.value.price_level?.let{it} ?: 1)){
            stars +="*"
        }
        Text("Price level: " + stars, fontSize = 18.sp)

    }

}





