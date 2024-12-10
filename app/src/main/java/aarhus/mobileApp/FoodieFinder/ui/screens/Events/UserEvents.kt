package aarhus.mobileApp.FoodieFinder.ui.screens.Events

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.events.ManageEvents
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.FriendsEventsScaffold
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun UserEvents(currentUser: UserFB?, onBackClicked: () -> Unit, onAddClicked: () -> Unit, onEnterClicked: (String) -> Unit) {
    val user = remember { mutableStateOf<UserFB?>(currentUser) }
    val authService = remember{ AuthService() }
    val scope = rememberCoroutineScope()

    val userService = remember { UserFBService() }

    LaunchedEffect(key1 = Unit) {
        // TODO HARD CODED
        try {

            //user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")
        }
        catch(e: Exception) {
            Log.v("LOG", e.message.toString())
        }
    }

  //  val navController = rememberNavController()

   /* NavHost(navController = navController, startDestination = "manageEvents") {
        composable("manageEvents") {
            //ManageEvents(user.value, on/*, scope, navController*/)
        }
        composable("enterEvent/{eventName}") { backStackEntry ->
            val eventID = backStackEntry.arguments?.getString("eventName") ?: "Unknown Event"
            EnterEventScreen(eventID, navController, scope, user.value)
        }
    }*/
    FriendsEventsScaffold(text = "Events", addClicked = onAddClicked, backClicked = onBackClicked) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            ManageEvents(user.value, onEnterClicked/*scope*/)
        }
    }


}