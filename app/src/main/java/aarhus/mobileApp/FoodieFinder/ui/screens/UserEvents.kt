package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.AddEventButton
import aarhus.mobileApp.FoodieFinder.ui.components.events.ManageEvents
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

@Composable
fun UserEvents() {
    val user = remember { mutableStateOf<UserFB?>(null) }
    val authService = remember{ AuthService() }
    val scope = rememberCoroutineScope()

    val userService = remember { UserFBService() }


    LaunchedEffect(key1 = Unit) {

        // TODO HARD CODED
        try {

            user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")
        }
        catch(e: Exception) {
            Log.v("LOG", e.message.toString())
        }
    }

    Column {
        ManageEvents(user.value, scope)
    }
}