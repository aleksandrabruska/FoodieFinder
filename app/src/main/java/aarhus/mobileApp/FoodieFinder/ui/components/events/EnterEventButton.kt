package aarhus.mobileApp.FoodieFinder.ui.components.events

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EnterEventButton(name: String) {
    Button(onClick = {}) {
        Text(name)
    }
}