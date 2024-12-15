package aarhus.mobileApp.FoodieFinder.ui.components.events.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddFriendToEventButton(scope: CoroutineScope, user: UserFB, event: EventFB,
                           nonParticipants: MutableList<UserFB>, participants: MutableList<UserFB>,
                           addingMode: MutableState<Boolean>
) {
    val eventService = remember { EventFBService() }
    val userService = remember { UserFBService() }


    //onclick -> remove from nonparticipants, add to participants
    Column() {

    }
    Column {
        Box(Modifier.clickable {
            addingMode.value = false
            scope.launch {
                try {
                    eventService.addUserToEvent(event, user.id)
                    userService.addEvent(user.id, event.id)
                    nonParticipants.remove(user)
                    participants.add(user)
                } catch (e: Exception) {
                    e.message?.let { Log.v("EVENT", it) }
                }

            }
        }) {
            FriendItem(removable = false, friend = user)
        }

    }
}