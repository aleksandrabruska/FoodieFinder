package aarhus.mobileApp.FoodieFinder.ui.components.events.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.components.events.participants.ManageParticipants
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManageFriendsOnEvent(/*scope: CoroutineScope,*/ event: EventFB, user: UserFB, isOwner: Boolean) {

    val friends = remember { mutableStateListOf<UserFB>() }
    val userService = remember { UserFBService() }
    val participants = remember { mutableStateListOf<UserFB>() }
    val nonParticipants = remember { mutableStateListOf<UserFB>() }
    val addingMode = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(true) }
    LaunchedEffect(key1 = Unit) {
        friends.addAll(userService.getFriendsOfAUser(user.id))

        event.participants.forEach { userID ->

            userService.getUser(userID)?.let { participant ->
                participants.add(participant)
            }
        }

        friends.forEach { friend ->
            if(event.id !in friend.events)
                nonParticipants.add(friend)
        }
        isLoading.value = false;
    }
    if(!isLoading.value) {
        Spacer(modifier = Modifier.height(20.dp))
        ManageParticipants(/*scope, */user,
            event,
            nonParticipants,
            participants,
            isOwner,
            addingMode
        )
        //every one who is not participating, but is a friend
    }
    else{
        Loader()
    }



}