package aarhus.mobileApp.FoodieFinder.ui.components.events.participants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.AddFriendToEventButton
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.FriendOnEvent
//import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.RemoveFriendFromEventButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ManageParticipants(user: UserFB, event: EventFB,
                       nonParticipants: MutableList<UserFB>, participants: MutableList<UserFB>,
                       isOwner: Boolean, addingMode: MutableState<Boolean>) {
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

                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Participants",textAlign = TextAlign.Center, fontSize = 25.sp)
                    Spacer(modifier = Modifier.padding(10.dp))
                    val scope = rememberCoroutineScope()
                    participants.forEach { participant ->
                        if(participant.id != user.id) {
                            FriendOnEvent(
                                scope,
                                participant,
                                event,
                                nonParticipants,
                                participants,
                                isOwner
                            )
                            Spacer(Modifier.padding(10.dp))
                        }
                    }
                    if(isOwner && !addingMode.value){
                        Button(onClick = {addingMode.value = true},
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
                            modifier = Modifier
                                .padding(10.dp, 10.dp, 0.dp, 0.dp)
                                .clip(CutCornerShape(20.dp))
                                .height(40.dp)){
                            Text("+", textAlign = TextAlign.Center, fontSize = 25.sp)
                        }
                    }
                    if(isOwner && addingMode.value) {
                        Text("Who do you want to add to this event?", fontStyle = FontStyle.Italic)
                        nonParticipants.forEach { friend ->
                            Row() {
                                AddFriendToEventButton(scope, friend, event, nonParticipants, participants, addingMode)
                            }
                            Spacer(Modifier.padding(10.dp))
                        }
                    }
                }
            }
        }
    }


}