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

    inputField("Enter your friends' email", true, email)


    Button(onClick = {
        if (email.value !in user.friends || email.value !in friendsState) {
            scope.launch {
                found.value = service.getUserByEmail(email.value)

                found.value?.let { friendToAdd ->
                    service.addFriend(friendToAdd.id, user.email) // add to theirs friends list myself
                    service.addFriend(user.id, friendToAdd.email) // add to my friends list them
                    friendsState.add(friendToAdd.email)
                }
                email.value = ""


            }
        }
    }) {
        Text("Add!")
    }


    return found.value
}