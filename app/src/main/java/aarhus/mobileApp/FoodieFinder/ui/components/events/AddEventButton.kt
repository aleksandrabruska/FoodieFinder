package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.domain.Date
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddEventButton(user: UserFB/*, scope: CoroutineScope, events: MutableList<EventFB>*/) {
    val name = remember { mutableStateOf<String>("") }
    val date = remember { mutableStateOf<String>("") }
    val eventService = remember{EventFBService()}
    val userFBService = remember{UserFBService()}
    val scope = rememberCoroutineScope()
    val message = remember{mutableStateOf<String?>(null)}

    Column() {
        name.value = inputField("enter name of your event", true, name, condition = {ch -> ch >= ' '})
        date.value = inputField("enter a date", true, date)

        Button(onClick = {
            scope.launch {

                try {
                    Date(date.value)
                    if (name.value.isBlank()) {
                        throw Exception("BAD NAME")
                    }
                    
                    val eventToAdd = EventFB("", name.value, date.value, user.id, arrayListOf(user.id))
                    val eventID = eventService.saveEvent(eventToAdd)
                    userFBService.addEvent(user.id, eventID)

                    name.value = ""
                    date.value = ""

                    eventToAdd.id = eventID
                    message.value = "Added!"
                    //events.add(eventToAdd)
                } catch(e: Exception) {
                    message.value = e.message
                }
            }
        }) {
            Text("Add")
        }

        message.value?.let {
            Text(it)
        }
    }
}