package aarhus.mobileApp.FoodieFinder.ui.components.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteButton(action: () -> Unit){
    OutlinedCard(
        modifier = Modifier.padding(10.dp, 0.dp).width(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
    ) {
        Box(Modifier.clickable {
            action()
        }.padding(10.dp, 0.dp)) {
            Text(" X ")
        }
    }
}