package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.friends.UserDetails
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@Composable
fun ManageFriendsOnEvent(/*scope: CoroutineScope,*/ event: EventFB, user: UserFB, isOwner: Boolean) {

    val friends = remember { mutableStateListOf<UserFB>() }
    val userService = remember { UserFBService() }
    val participants = remember { mutableStateListOf<UserFB>() }
    val nonParticipants = remember { mutableStateListOf<UserFB>() }

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

    }

    Spacer(modifier = Modifier.height(20.dp))
    ManageParticipants(/*scope, */user, event, nonParticipants, participants, isOwner)
    //every one who is not participating, but is a friend




}