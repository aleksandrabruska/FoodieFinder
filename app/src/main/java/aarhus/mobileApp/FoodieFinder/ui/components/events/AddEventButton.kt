package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddEventButton(user: UserFB, scope: CoroutineScope, events: MutableList<EventFB>) {
    val name = remember { mutableStateOf<String>("") }
    val date = remember { mutableStateOf<String>("") }
    val eventService = remember{EventFBService()}
    val userFBService = remember{UserFBService()}

    name.value = inputField("enter name of your event", true, name)
    date.value = inputField("enter a date", true, date)

    Button(onClick = {
      scope.launch {
          val eventToAdd = EventFB("", name.value, date.value, user.id, arrayListOf(user.id))
          val eventID = eventService.saveEvent(eventToAdd)
          userFBService.addEvent(user.id, eventID, true)

          name.value = ""
          date.value = ""

          eventToAdd.id = eventID
          events.   add(eventToAdd)
      }
    } ){
      Text("Add")
    }
}