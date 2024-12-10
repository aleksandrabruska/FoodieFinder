package aarhus.mobileApp.FoodieFinder.ui.screens.Events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.EventDetails
import aarhus.mobileApp.FoodieFinder.ui.components.events.ManageFriendsOnEvent
import aarhus.mobileApp.FoodieFinder.ui.components.friends.ManageFriendButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun EnterEventScreen(eventID: String,/*, navController: NavController, scope: CoroutineScope, */user: UserFB?) {
    val eventService = remember{ EventFBService() }
    val event = remember{ mutableStateOf<EventFB?>(null)}
    val isOwner = remember{mutableStateOf<Boolean>(false)}

    LaunchedEffect(key1 = Unit) {
        event.value = eventService.getEventByID(eventID)
    }

    Column {
        user?.let { userEntered ->
            event.value?.let { eventFound ->

                if (eventFound.ownerId == userEntered.id)
                    isOwner.value = true

                EventDetails(eventFound, userEntered)
                ManageFriendsOnEvent(/*scope,*/ eventFound, userEntered, isOwner.value)

            }
        }

        Button(onClick = { } /*navController.popBackStack() }*/) {
            Text("Back")
        }
    }
}