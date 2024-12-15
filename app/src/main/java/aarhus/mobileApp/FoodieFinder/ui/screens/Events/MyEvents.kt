package aarhus.mobileApp.FoodieFinder.ui.screens.Events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.components.events.EventsList

import aarhus.mobileApp.FoodieFinder.ui.scaffolding.FriendsEventsScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch

@Composable
fun MyEvents(currentUser: UserFB?, onBackClicked: () -> Unit, onAddClicked: () -> Unit, onEnterClicked: (String) -> Unit) {
    val user = remember { mutableStateOf<UserFB?>(currentUser) }
    val scope = rememberCoroutineScope()
    val events = remember { mutableStateOf<List<EventFB>>(emptyList()) }
    val eventService = remember{ EventFBService() }
    val isLoading = remember { mutableStateOf(true) }
    val userService = remember { UserFBService() }
    suspend fun refresh() {
        isLoading.value = true
        user.value?.let {
            events.value = eventService.getUsersEvents(it.id)
        }
        isLoading.value = false
    }
    LaunchedEffect(key1 = Unit) {
        refresh()
    }


    FriendsEventsScaffold(text = "Events", addClicked = onAddClicked, backClicked = onBackClicked) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if(!isLoading.value) {
                EventsList(user = user.value,
                    events = events.value,
                    onEnterClicked = onEnterClicked,
                    onChange = {
                        scope.launch {
                            refresh()
                        }
                    })
            }
            else{
                Loader()
            }
        }
    }


}