package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.EventActivity
import aarhus.mobileApp.FoodieFinder.MainActivity
import aarhus.mobileApp.FoodieFinder.MapsActivity
import aarhus.mobileApp.FoodieFinder.SearchActivity
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.BasicRestaurantInfoBox
//import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.RestaurantDetailsBox
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.RestaurantPhoto
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.RestaurantInfoScaffold
import android.content.Intent
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
import kotlinx.coroutines.launch


@Composable
fun RestaurantInfo(restaurant: Restaurant, details: () -> Unit, navigate: () -> Unit) {
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    RestaurantInfoScaffold(checkClicled = {
        val intent = Intent(context, EventActivity::class.java)
        intent.putExtra("restaurant_chosen_id", restaurant.id)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(intent)
    },
        locationClicked = {
            scope.launch {
                val intent = Intent(context, MapsActivity::class.java)
                intent.putExtra("restaurant_lat", restaurant.lat)
                intent.putExtra("restaurant_lng", restaurant.lng)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                context.startActivity(intent)
            }
        },
        infoClicked = details,
        arrowClicked = navigate
    ) {
    if (restaurant != null) {
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
                    //Text(restaurant.value?.name?.let { it } ?: "None",
                    Text(restaurant.name?.let { it } ?: "None",
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center)
                    RestaurantPhoto(restaurant.photoReference)
                    BasicRestaurantInfoBox(restaurant)
                    //TODO: move that up??
                    /*
                    Button (onClick = {
                        scope.launch {
                            val intent = Intent(context, MapsActivity::class.java)
                            intent.putExtra("restaurant_lat", restaurant.lat)
                            intent.putExtra("restaurant_lng", restaurant.lng)
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            context.startActivity(intent)
                        }
                    }){
                        Text("See on map")
                    }
                    Button(onClick = details){
                        Text("Details")
                    }
                    Button (onClick = navigate){
                        Text("Next")
                    }
                    Button({}){
                        Text("Choose")
                    }*/
                }
            }
        }
    }



}