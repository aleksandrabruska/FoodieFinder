package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EventDetails(event: EventFB, userEntered: UserFB) {
    if(event.ownerId == userEntered.id)
        Text("You are the owner of this event")
    else
        Text("You are not the owner of this event")

    Text("name: " + event.name)
    Text("date: " + event.date)

    /*
    Text("participants:")
    event.participants.forEach {  user ->
        Text(" - " + user)
    }
    Text("already posted:")
    event.participants_already_posted.forEach { user ->
        Text(" - " + user)
    }
    Text("already voted:")
    event.participants_already_voted.forEach { user ->
        Text(" - " + user)
    }
    Text("restaurants:")
    event.restaurants.forEach { restaurant ->
        Text(" - " + restaurant)
    }*/
}