package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RemoveFriendFromEventButton(scope: CoroutineScope, user: UserFB, event: EventFB, nonParticipants: MutableList<UserFB>, participants: MutableList<UserFB>) {
    val eventService = remember { EventFBService() }
    val userService = remember { UserFBService() }


    Column() {
        Text(user.name)
    }
    Column {
        Button(onClick = {

            scope.launch {
                try {
                    eventService.removeUserFromEvent(event, user.id)
                    userService.removeEvent(user.id, event.id)

                    nonParticipants.add(user)
                    participants.remove(user)
                } catch (e: Exception) {
                    e.message?.let { Log.v("EVENT", it) }

                }
            }
        }) {
            Text("+")
        }

    }
}