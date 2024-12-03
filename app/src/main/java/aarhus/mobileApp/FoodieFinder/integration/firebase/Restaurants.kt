package aarhus.mobileApp.FoodieFinder.integration.firebase

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun Restaurants() {
    val service = remember{ RestaurantFBService() }
    var models by remember { mutableStateOf<List<RestaurantFB>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        models = service.getRestaurants()
    }
    Column {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            models.forEach{ Restaurant(it) }
        }
        Button(
            onClick = {
                val a = RestaurantFB(
                    "",
                    "someID" + Random.nextInt(0, 100),
                    Random.nextInt(0, 4),
                    arrayListOf(
                        "user" + Random.nextInt(0, 100).toString(),
                        "user" + Random.nextInt(0, 100).toString(),
                        "user" + Random.nextInt(0, 100).toString(),
                        "user" + Random.nextInt(0, 100).toString(),
                        )
                )
                service.saveRestaurant(a)
            }
        ) {
            Text("ADD SOMETHING")
        }
    }
}

@Composable
fun Restaurant(model: RestaurantFB) {
    Column {
        Text("(auto generated) Id: " + model.id)
        Text("restaurant id:" + model.restaurant_id)
        Text("number of votes: " + model.number_of_votes)
        model.users_id.forEach {Text(it)}
    }
}