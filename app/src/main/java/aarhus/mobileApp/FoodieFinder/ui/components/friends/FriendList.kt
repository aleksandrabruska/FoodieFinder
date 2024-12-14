package aarhus.mobileApp.FoodieFinder.ui.components.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.DeleteButton
import aarhus.mobileApp.FoodieFinder.ui.components.events.friends.FriendItem
import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FriendList(friends: List<UserFB>, user: UserFB, scope: CoroutineScope, onChange: () -> Unit) {
    val service = remember { UserFBService() }
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }
    Column(modifier = Modifier.padding(10.dp).scrollable(orientation = Orientation.Vertical,
        state = rememberScrollableState { delta ->
            offset += delta
            delta
        }).verticalScroll(scrollState, offset < -40) ){
        friends.forEach { friend ->
            FriendItem(removable = true, friend) {  scope.launch {
                service.removeFriend(user.id, friend.email)
                service.removeFriend(friend.id, user.email)
                onChange()
            }}

            Spacer(modifier = Modifier.padding(5.dp))
        }

    }
}