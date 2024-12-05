package aarhus.mobileApp.FoodieFinder.ui.components.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchAndAddFriend(scope: CoroutineScope, user: UserFB, friendsState: MutableList<String>) : UserFB? {
    val email = remember { mutableStateOf<String>("") }
    val service = remember {UserFBService()}
    val found = remember {mutableStateOf<UserFB?>(null)}

    email.value = inputField("Enter your friends' email", true, email)

    if (email.value in user.friends) {
        return null
    }

    Button(onClick = {
        scope.launch {
            found.value = service.getUserByEmail(email.value)

            found.value?.let {
                service.addFriend(user.id, it.email)
                friendsState.add(it.email)
            }

            email.value = ""

        }
    }) {
        Text("Add!")
    }


    return found.value
}