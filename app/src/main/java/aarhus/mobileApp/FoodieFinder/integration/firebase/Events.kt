package aarhus.mobileApp.FoodieFinder.integration.firebase

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import android.util.Log
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

@Composable
fun Events() {
    val service = remember{ EventFBService() }
    var models by remember { mutableStateOf<List<EventFB>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        models = service.getEvents()
    }

    Column {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            models.forEach {
                //Event(it)
            }
        }
    }

    Button(onClick = {
        val event = EventFB(
            "", "fancy burger", "12.02", ArrayList(listOf("janek", "dzbanek", "maniek")),
            ArrayList(listOf("dzbanek")), ArrayList(listOf("maniek")), ArrayList(listOf("someid1", "someid2"))
        )

        Log.v("EVENT", "cos innego")

        //cservice.saveEvent(event)

        Log.v("EVENT", event.id)

    }) {Text("ADD")}

}


@Composable()
fun Event(ev: EventFB) {
    Column() {
        Text("id " + ev.id)
        Text("name " + ev.name)
        ev.participants.forEach {
            Text("  participant: " + it)
        }
        ev.restaurants.forEach {
            Text("   restaurant: " + it)
        }
    }

}