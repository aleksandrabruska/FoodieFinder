package aarhus.mobileApp.FoodieFinder

import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.ui.screens.EventView
import aarhus.mobileApp.FoodieFinder.ui.screens.RestaurantSearch
import aarhus.mobileApp.FoodieFinder.ui.theme.FoodieFinderTheme
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.firebase.components.Component
import kotlinx.coroutines.launch

class SearchActivity : ComponentActivity(){

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            FoodieFinderTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        RestaurantSearch()
                    }
                }
            }
        }
    }
}