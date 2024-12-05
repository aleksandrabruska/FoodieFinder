package aarhus.mobileApp.FoodieFinder.ui.components.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ManageFriendButton(service: UserFBService, scope: CoroutineScope, user: UserFB, model: UserFB, friendsState: MutableList<String>) {
    if (user.id == model.id)
        return

    val added = model.email in friendsState

    Button(onClick = {
        scope.launch {
            if (!added) {
                service.addFriend(user.id, model.email)
                friendsState.add(model.email)
            } else {
                service.removeFriend(user.id, model.email)
                friendsState.remove(model.email)
            }
        }
    }) {
        if (!added) {
            Text("ADD")
        } else {
            Text("REMOVE")
        }
    }
}