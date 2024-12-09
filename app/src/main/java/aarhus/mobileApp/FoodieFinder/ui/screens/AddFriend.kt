package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.friends.ManageFriendButton
import aarhus.mobileApp.FoodieFinder.ui.components.friends.SearchAndAddFriend
import aarhus.mobileApp.FoodieFinder.ui.components.friends.UserDetails
import androidx.compose.foundation.layout.Column
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
        user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")

    }

    Column () {
        user.value?.let { user ->
            val friendsState = remember { mutableStateListOf(*user.friends.toTypedArray()) }

            UserDetails(user)

            SearchAndAddFriend(scope, user, friendsState)

            models.forEach {
                friend(userService, scope, user, it, friendsState)

            }
        }


    }

}

@Composable
fun friend(service: UserFBService, scope: CoroutineScope, user: UserFB, model: UserFB, friendsState: MutableList<String>) {
    if(model.email in friendsState) {
        Text("\nYour friends")
        UserDetails(model)
        ManageFriendButton(service, scope, user, model, friendsState)
    }
}