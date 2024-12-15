package aarhus.mobileApp.FoodieFinder
import aarhus.mobileApp.FoodieFinder.navigation.FoodieFinderNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import aarhus.mobileApp.FoodieFinder.ui.theme.FoodieFinderTheme
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import com.google.android.gms.location.LocationServices
import aarhus.mobileApp.FoodieFinder.integration.googleMaps.MapsService


class MainActivity : ComponentActivity() {
    private val locationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodieFinderTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize())
                    { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            FoodieFinderNavigation(MapsService(locationClient))
                        }
                }
            }
        }
    }
}

