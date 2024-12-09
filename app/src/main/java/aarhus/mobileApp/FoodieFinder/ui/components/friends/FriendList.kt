package aarhus.mobileApp.FoodieFinder.ui.components.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FriendList(friends: List<UserFB>, user: UserFB, scope: CoroutineScope, onChange: () -> Unit) {
    val service = remember { UserFBService() }

    friends.forEach { friend ->
        Text("*" + friend.name + "*")
        ManageFriendButton("remove", action = {
            scope.launch {
                service.removeFriend(user.id, friend.email)
                service.removeFriend(friend.id, user.email)
                onChange()
            }
        }
        )
    }
}