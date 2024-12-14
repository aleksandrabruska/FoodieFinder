package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.ManageEvents
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.FriendsEventsScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
/*
@Composable
fun MyEvents(currentUser: UserFB?, onAddEventClicked: () -> Unit, onBackClicked: () -> Unit, onEnterClicked: (String) -> Unit) {
    val user = remember { mutableStateOf<UserFB?>(currentUser) }
    val authService = remember{ AuthService() }
    val events = remember { mutableStateOf<List<EventFB>>(emptyList()) }
    val eventService = remember{ EventFBService() }
    val scope = rememberCoroutineScope()
    suspend fun refresh() {
        user.value?.let {
            events.value = eventService.getUsersEvents(it.id)
        }
    }
    LaunchedEffect(key1 = Unit) {
        // TODO HARD CODED
       // user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")
        refresh()
    }


    FriendsEventsScaffold(text = "Events", addClicked = onAddEventClicked, backClicked = onBackClicked ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(20.dp))
            Text("**display friends here**")
            ManageEvents(user = user.value, events = events.value, onEnterClicked =  onEnterClicked, onChange = {
                scope.launch {
                    refresh()
                }
            })
        }
    }
}*/