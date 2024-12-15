package aarhus.mobileApp.FoodieFinder.ui.components.events.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.DeleteButton
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FriendOnEvent(scope: CoroutineScope, user: UserFB, event: EventFB,
                  nonParticipants: MutableList<UserFB>, participants: MutableList<UserFB>,
                  isOwnerLogged: Boolean) {
    val eventService = remember { EventFBService() }
    val userService = remember { UserFBService() }
    FriendItem(removable = isOwnerLogged, friend = user, onDelete = { scope.launch {
        try {
            eventService.removeUserFromEvent(event, user.id)
            userService.removeEvent(user.id, event.id)

            nonParticipants.add(user)
            participants.remove(user)
        } catch (e: Exception) {
            e.message?.let { Log.v("EVENT", it) }

        }
    }
    })

}