package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun addFriend() {
    val user = remember { mutableStateOf<UserFB?>(null) }
    val authService = remember{ AuthService() }
    val scope = rememberCoroutineScope()

    var models by remember { mutableStateOf<List<UserFB>>(emptyList()) }
    val userService = remember {UserFBService()}

    LaunchedEffect(key1 = Unit) {
        models = userService.getUsers()

        // TODO HARD CODED
        user.value = authService.logIn("jas@gmail.pl", "aaaaaaaa")

    }

    Column () {

        user.value?.let {
            val friendsState = remember { mutableStateListOf(*it.friends.toTypedArray()) }

            Text(user.value!!.id)
            Text(user.value!!.email)
            Text(user.value!!.name)
            Text("YOUR FRIENDS:")
            friendsState.forEach {
                Text("  friend: " + it)
            }

            models.forEach {
                friend(userService, scope, user.value!!, it, friendsState)

            }
        }


    }

}

@Composable
fun friend(service: UserFBService, scope: CoroutineScope, user: UserFB, model: UserFB, friendsState: MutableList<String>) {
    val added = model.email in friendsState

    if (user.id != model.id) {

        Text("\n--IN DB--")
        Text(model.email)
        Text(model.name)

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
}