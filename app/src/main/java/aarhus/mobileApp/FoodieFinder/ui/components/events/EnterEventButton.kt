package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun EnterEventButton(event: EventFB, navController: NavController) {
    Button(onClick = {
        navController.navigate("enterEvent/${event.id}")
    }) {
        Text(event.name)
    }
}