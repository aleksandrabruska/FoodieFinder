package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.MapsActivity
import aarhus.mobileApp.FoodieFinder.SearchActivity
import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.integration.model.User
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.ParticipantsList
import aarhus.mobileApp.FoodieFinder.ui.components.restaurants.SuggestionsList
import aarhus.mobileApp.FoodieFinder.ui.scaffolding.EventScaffold
import android.content.Intent

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun EventView(event: Event){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    var offset by remember { mutableStateOf(0f) }
    EventScaffold(event.name,Modifier, {}) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .scrollable(orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }).verticalScroll(scrollState, offset < -40) ){
            //Text(event.name,  textAlign = TextAlign.Center, fontSize = 25.sp)
            ParticipantsList(event.participants)
            SuggestionsList(event.venuesIDs){ scope.launch {
                val intent = Intent(context, SearchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                context.startActivity(intent)
            }}

        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewEvent(){
    //FoodieFinderTheme(dynamicColor = false) {
    val u1 = User("123", "Eleonora")
    val u2 = User("345", "Anne")
    val list = listOf(u1,u2)
    var event = Event("Eleonora birthday", list, emptyList())
    EventView(event)
    //}
}
