package aarhus.mobileApp.FoodieFinder

import aarhus.mobileApp.FoodieFinder.ui.theme.FoodieFinderTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import aarhus.mobileApp.FoodieFinder.ui.screens.Map
class MapsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val lat = intent.getDoubleExtra("restaurant_lat", 0.0)
        val lng = intent.getDoubleExtra("restaurant_lng", 0.0)
        setContent {
            FoodieFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Map(lat, lng)
                    }
                }
            }
        }
    }
}