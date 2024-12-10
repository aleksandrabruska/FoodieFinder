package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.friends.FriendList
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.FriendsEventsScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun MyFriends(currentUser: UserFB?, onAddFriendClicked: () -> Unit, onBackClicked: () -> Unit) {

    val user = remember { mutableStateOf<UserFB?>(currentUser) }
    val authService = remember{ AuthService() }

    val friends = remember { mutableStateOf<List<UserFB>>(emptyList()) }
    val userService = remember { UserFBService() }
    val scope = rememberCoroutineScope()


    suspend fun refresh() {
        user.value?.let {
            friends.value = userService.getFriendsOfAUser(it.id)
        }
    }


    LaunchedEffect(key1 = Unit) {
        // TODO HARD CODED
        //  user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")

        refresh()
    }


    FriendsEventsScaffold(text = "Friends", addClicked = onAddFriendClicked, backClicked = onBackClicked) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(20.dp))
            //Text("**display friends here**")
            user.value?.let {
                FriendList(friends.value, it, scope, onChange = {
                    scope.launch {
                        refresh()
                    }
                })
            }
        }
    }


}
