package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.BuildConfig
import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import android.graphics.Bitmap

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import coil.compose.SubcomposeAsyncImage

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RestaurantDetailsBox(restaurant_: Restaurant?) {

    val restaurant = remember { mutableStateOf<Restaurant?>(restaurant_) }
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(restaurant.value?.summary?.let { it } ?: "None", textAlign = TextAlign.Center, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(restaurant.value?.address?.let { it } ?: "None", textAlign = TextAlign.Center, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Rating " + (restaurant.value?.rating?.let { it.toString() } ?: "None")
                + " based on " + (restaurant.value?.ratingsNumber?.let { it.toString() } ?: "None")
                + " reviews", textAlign = TextAlign.Center, fontSize = 18.sp)
        var stars = ""
        for (i in 1..(restaurant.value?.price_level?.let{it} ?: 1)){
            stars +="*"
        }
        Text("Price level: " + stars, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Opening hours", textAlign = TextAlign.Center, fontSize = 18.sp)
        for(i in 1..< (restaurant.value?.openingHours?.size ?: 0)){
            Text(restaurant.value?.openingHours?.elementAt(i).let { it.toString() } ?: "None", textAlign = TextAlign.Center, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Find more information on " + restaurant.value?.website?.let { it.toString() } ?: "None", textAlign = TextAlign.Center, fontSize = 15.sp)
    }

}





