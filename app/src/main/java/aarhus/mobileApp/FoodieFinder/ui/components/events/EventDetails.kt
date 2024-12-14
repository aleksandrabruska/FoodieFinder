package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventDetails(event: EventFB, userEntered: UserFB) {

    Column(modifier = Modifier.padding(10.dp)){
        if (event.ownerId == userEntered.id)
            Text("You are the owner of this event", fontStyle = FontStyle.Italic)


        Text("Date: " + event.date, fontSize = 25.sp)
    }
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