package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.ui.scaffolding.FriendsEventsScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyEvents(onAddEventClicked: () -> Unit, onBackClicked: () -> Unit) {
    FriendsEventsScaffold(text = "Events", addClicked = onAddEventClicked, backClicked = onBackClicked ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(20.dp))
            Text("**display friends here**")

        }
    }
}