package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.BasicScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddEventButton(user: UserFB, onBackClicked: () -> Unit/*, scope: CoroutineScope, events: MutableList<EventFB>*/) {
    val name = remember { mutableStateOf<String>("") }
    val date = remember { mutableStateOf<String>("") }
    val eventService = remember{EventFBService()}
    val userFBService = remember{UserFBService()}
    val scope = rememberCoroutineScope()

    BasicScaffold(sectionName = "Create an event", backClicked = onBackClicked) {
        Column(
            modifier = Modifier.padding(50.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(40.dp))
            name.value = inputField("enter name of your event", true, name)
            date.value = inputField("enter a date", true, date)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                scope.launch {
                    val eventToAdd =
                        EventFB("", name.value, date.value, user.id, arrayListOf(user.id))
                    val eventID = eventService.saveEvent(eventToAdd)
                    userFBService.addEvent(user.id, eventID)

                    name.value = ""
                    date.value = ""

                    eventToAdd.id = eventID
                    //events.add(eventToAdd)
                }
            }, modifier = Modifier
                .clip(CutCornerShape(20.dp))
                .fillMaxWidth(0.75f)
                .height(40.dp)) {
                Text("Add!", fontSize = 20.sp)

            }
        }
    }
}