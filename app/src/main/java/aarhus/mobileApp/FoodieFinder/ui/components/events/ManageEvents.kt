package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun ManageEvents(events: List<EventFB>, user: UserFB?, onChange: () -> Unit, onEnterClicked: (String) -> Unit/*, scope: CoroutineScope, navController: NavController*/) {
    val eventService = remember{EventFBService()}

    //val events = remember { mutableStateListOf<EventFB>() }
    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }

    user?.let { user ->
        LaunchedEffect(key1 = Unit) {
      //      events.addAll(eventService.getUsersEvents(user.id))
        }

        Column(modifier = Modifier.padding(10.dp).scrollable(orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }).verticalScroll(scrollState, offset < -40) ){

            events.forEach { event ->
                EnterEventButton(event, onChange, onEnterClicked)
                Spacer(modifier = Modifier.padding(5.dp))
            }

        }
    }
}