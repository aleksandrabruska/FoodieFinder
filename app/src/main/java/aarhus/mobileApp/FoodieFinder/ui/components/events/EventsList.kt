package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventsList(events: List<EventFB>, user: UserFB?, onChange: () -> Unit, onEnterClicked: (String) -> Unit) {
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }

    user?.let { user ->
        Column(modifier = Modifier.padding(10.dp).scrollable(orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }).verticalScroll(scrollState, offset < -40) ){

            events.forEach { event ->
                EventItem (event, event.ownerId == user.id, onChange, onEnterClicked)
                Spacer(modifier = Modifier.padding(5.dp))
            }

        }
    }
}