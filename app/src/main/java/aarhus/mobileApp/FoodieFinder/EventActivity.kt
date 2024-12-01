package aarhus.mobileApp.FoodieFinder

import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.integration.model.User
import aarhus.mobileApp.FoodieFinder.ui.screens.EventView
import aarhus.mobileApp.FoodieFinder.ui.screens.RestaurantSearch
import aarhus.mobileApp.FoodieFinder.ui.theme.FoodieFinderTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

class EventActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val newRestaurantID = intent.getStringExtra("restaurant_chosen_id")
            val u1 = User("123", "Eleonora")
            val u2 = User("345", "Anne")
            val list = listOf(u1,u2)
            var event = Event("Eleonora birthday", list, emptyList())

            if(newRestaurantID != null)
                event.venuesIDs = event.venuesIDs + newRestaurantID
            FoodieFinderTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        EventView(event)
                    }
                }
            }
        }
    }
}