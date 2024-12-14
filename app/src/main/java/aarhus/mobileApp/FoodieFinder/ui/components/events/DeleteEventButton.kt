package aarhus.mobileApp.FoodieFinder.ui.components.events

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeleteEventButton(event: EventFB, onChange: () -> Unit) {
    val userService = remember { UserFBService() }
    val eventService = remember { EventFBService() }
    val scope = rememberCoroutineScope()

    OutlinedCard(
        modifier = Modifier.padding(10.dp, 0.dp).width(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
    ) {
        Box(Modifier.clickable {

            scope.launch {
                event.participants.forEach { userId ->
                    userService.removeEvent(userId, event.id)
                }
                eventService.removeEvent(event.id)
                onChange()


            }
        }. padding(10.dp, 0.dp)) {
            Text(" X ")
        }
    }
}