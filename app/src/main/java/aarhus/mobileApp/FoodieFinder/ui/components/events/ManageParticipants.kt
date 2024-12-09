package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope

@Composable
fun ManageParticipants(scope: CoroutineScope, user: UserFB, event: EventFB, nonParticipants: MutableList<UserFB>, participants: MutableList<UserFB>, isOwner: Boolean) {
    Text("PARTICIPANTS")

    participants.forEach { participant ->
        Row(modifier = Modifier.background(Color(255, 0 ,0))) {
            if(isOwner && participant.id != user.id)
                RemoveFriendFromEventButton(scope, participant, event, nonParticipants, participants)
            else
                Text(participant.name)
        }


    }

    if(isOwner) {
        nonParticipants.forEach { friend ->
            Row() {
                AddFriendToEventButton(scope, friend, event, nonParticipants, participants)
            }
        }
    }

}