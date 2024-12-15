package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.domain.Date
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.BasicScaffold
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AddEvent(user: UserFB, onBackClicked: () -> Unit) {
    val name = remember { mutableStateOf<String>("") }
    val date = remember { mutableStateOf<String>("") }
    val eventService = remember { EventFBService() }
    val userFBService = remember { UserFBService() }
    val scope = rememberCoroutineScope()
    val message = remember { mutableStateOf<String?>(null) }
    var addingDone = remember { mutableStateOf(false) }

    BasicScaffold(sectionName = "Create an event", backClicked = onBackClicked) {
        Column(
            modifier = Modifier.padding(50.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(40.dp))
            name.value = inputField("enter name of your event", true, name, {ch -> ch >= ' '})
            date.value = inputField("enter a date as dd.mm", true, date)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    scope.launch {
                        try {
                            Date(date.value)
                            if (name.value.isBlank()) {
                                throw Exception("BAD NAME")
                            }

                            val eventToAdd =
                                EventFB("", name.value, date.value, user.id, arrayListOf(user.id))
                            val eventID = eventService.saveEvent(eventToAdd)
                            userFBService.addEvent(user.id, eventID)

                            name.value = ""
                            date.value = ""

                            eventToAdd.id = eventID
                            message.value = "Added!"
                            addingDone.value = true
                            Log.v("Added", "Added")
                            //onBackClicked()
                            //events.add(eventToAdd)
                        } catch (e: Exception) {
                            message.value = e.message
                            addingDone.value = false
                        }
                    }
                }, modifier = Modifier
                    .clip(CutCornerShape(20.dp))
                    .fillMaxWidth(0.75f)
                    .height(40.dp)
            ) {
                Text("Add")
            }
            Spacer(Modifier.padding(20.dp))
            message.value?.let {
                Text(it)
            }
            if(addingDone.value && message.value == "Added!"){
                onBackClicked()
            }

        }
    }
}