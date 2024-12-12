package aarhus.mobileApp.FoodieFinder.ui.components.events.participants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.AddFriendToEventButton
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.RemoveFriendFromEventButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ManageParticipants(/*scope: CoroutineScope,*/ user: UserFB, event: EventFB, nonParticipants: MutableList<UserFB>, participants: MutableList<UserFB>, isOwner: Boolean) {
    Box(    //to center the whole thing
       // modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .padding(10.dp)) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .fillMaxWidth(0.9f)
            )
            {

                Column(modifier = Modifier.padding(25.dp)) {
                    Text("PARTICIPANTS")
                    val scope = rememberCoroutineScope()
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
            }
        }
    }


}