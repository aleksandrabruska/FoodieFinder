package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventItem(event: EventFB, removable: Boolean, onChange: () -> Unit, onEnterClicked: (id:String) -> Unit ) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onEnterClicked(event.id) }) {
        Row(modifier = Modifier.padding(20.dp, 10.dp), verticalAlignment = Alignment.Bottom) {
            Text(event.name, textAlign = TextAlign.Left, fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth(0.8f))
            if(removable) {
                DeleteEventButton(event, onChange)
            }
        }
    }
}