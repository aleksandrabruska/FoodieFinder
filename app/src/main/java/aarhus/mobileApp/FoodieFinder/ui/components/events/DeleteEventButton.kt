package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeleteEventButton(event: EventFB, scope: CoroutineScope, events: MutableList<EventFB>) {
    val userService = remember{UserFBService()}
    val eventService = remember{EventFBService()}


    Button(onClick = {
        scope.launch {
            event.participants.forEach { userId ->
                userService.removeEvent(userId, event.id)
            }
            eventService.removeEvent(event.id)

            events.remove(event)
        }
    }) {
        Text("X")
    }
}