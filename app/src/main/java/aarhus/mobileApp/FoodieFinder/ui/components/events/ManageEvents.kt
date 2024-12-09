package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun ManageEvents(user: UserFB?, scope: CoroutineScope, navController: NavController) {
    val eventService = remember{EventFBService()}

    val events = remember { mutableStateListOf<EventFB>() }


    user?.let { user ->
        LaunchedEffect(key1 = Unit) {
            events.addAll(eventService.getUsersEvents(user.id))
        }

        Column {
            AddEventButton(user, scope, events)
            Text("")

            events.forEach { event ->
                Row {
                    EnterEventButton(event, navController)
                    if(event.ownerId == user.id)
                        DeleteEventButton(event, scope, events)
                }
            }

        }
    }
}