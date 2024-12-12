package aarhus.mobileApp.FoodieFinder.ui.screens.Events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.EventDetails
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.AddFriendToEventButton
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.ManageFriendsOnEvent
import aarhus.mobileApp.FoodieFinder.ui.components.events.restaurants.AddRestaurantToEvent
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.SuggestionsList
import aarhus.mobileApp.FoodieFinder.ui.components.events.restaurants.validateAdditionOfRestaurantToEvent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EnterEventScreen(eventID: String, user: UserFB?, newRestaurantId: String, newRestaurantName: String, addRestaurantClicked: () -> Unit) {
    val eventService = remember{ EventFBService() }
    val event = remember{ mutableStateOf<EventFB?>(null)}
    val isOwner = remember{mutableStateOf<Boolean>(false)}
    val message = remember{mutableStateOf<String>("")}
    val scope = rememberCoroutineScope()
    val trigger = remember { mutableStateOf(false) }

    LaunchedEffect(trigger.value) {
        scope.launch {
            event.value = eventService.getEventByID(eventID)
            Log.v("REF", "refreshing")
        }
    }

    Column {
        user?.let { userEntered ->
            event.value?.let { eventFound ->

                if (eventFound.ownerId == userEntered.id)
                    isOwner.value = true
                Column {
                    EventDetails(eventFound, userEntered)
                    ManageFriendsOnEvent(/*scope,*/ eventFound, userEntered, isOwner.value)
                    SuggestionsList(emptyList(), addRestaurantClicked)

                    //TODO
                    if(newRestaurantId != "0") {
                        Button(onClick = {
                            scope.launch {
                                trigger.value = !trigger.value
                                Log.v("REF", "CHANGED")
                                try {
                                    AddRestaurantToEvent(userEntered, newRestaurantId, eventFound, newRestaurantName)
                                    message.value = "Added!"
                                }
                                catch(e: Exception) {
                                    message.value = e.message.toString()
                                }
                            }
                        }) {
                            Text("Confirm your choice")
                        }
                    }

                    Text(message.value)

                }
            }
        }

        Button(onClick = { } /*navController.popBackStack() }*/) {
            Text("Back")
        }
    }
}