package aarhus.mobileApp.FoodieFinder.ui.screens.Events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.EventDetails
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.ManageFriendsOnEvent
import aarhus.mobileApp.FoodieFinder.ui.components.events.participants.setUserStatus
import aarhus.mobileApp.FoodieFinder.ui.components.events.restaurants.AddRestaurantToEvent
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.SuggestionsList
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EnterEventScreen(eventID: String, user: UserFB?, newRestaurantId: String, newRestaurantName: String, addRestaurantClicked: () -> Unit) {
    val eventService = remember{ EventFBService() }
    val event = remember{ mutableStateOf<EventFB?>(null)}
    val message = remember{mutableStateOf<String>("")}
    val scope = rememberCoroutineScope()
    val trigger = remember { mutableStateOf(false) }
    val suggesterRestaurants = remember{mutableStateListOf<RestaurantFB>()}
    val restaurantService = remember{RestaurantFBService()}

    // UserStatus
    val isOwner = remember{mutableStateOf(false)}
    val thisUserAlreadyVoted = remember {mutableStateOf(false)}
    val thisUserAlreadyPosted = remember {mutableStateOf(false)}


    LaunchedEffect(trigger.value) {
        scope.launch {
            event.value = eventService.getEventByID(eventID)
            event.value?.let { ev ->
                suggesterRestaurants.clear()
                ev.restaurants.forEach {
                    restaurantService.searchByPlaceAndEventId(it, ev.id)?.let { foundRes ->
                        suggesterRestaurants.add(foundRes)
                    }
                }
            }
            Log.v("REF", "refreshing")
        }
    }

    Column {
        Button(onClick = {trigger.value = !trigger.value}) {
            Text("REFRESH")
        }

        user?.let { userEntered ->
            event.value?.let { eventFound ->

                setUserStatus(eventFound, userEntered, isOwner, thisUserAlreadyVoted, thisUserAlreadyPosted)

                Column {
                    EventDetails(eventFound, userEntered)
                    ManageFriendsOnEvent(/*scope,*/ eventFound, userEntered, isOwner.value)
                    SuggestionsList(suggesterRestaurants, thisUserAlreadyPosted , thisUserAlreadyVoted, addRestaurantClicked, vote =
                        { votedForRes ->
                            scope.launch {
                                restaurantService.addVote(votedForRes)
                                eventService.addParticipantAlreadyVoted(eventFound, userEntered.id)
                                thisUserAlreadyVoted.value = true
                                trigger.value = !trigger.value
                            }

                        })

                    //TODO
                    if(newRestaurantId != "0" && !thisUserAlreadyPosted.value) {
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