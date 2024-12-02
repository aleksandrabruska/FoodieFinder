package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.internal.composableLambdaN
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.howard.simplemapapp.intergration.google.MapsService
import android.location.Location


//TODO - add loading
private const val PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
@Composable
fun RestaurantSearch(mapsService: MapsService){
    val restaurantsService = remember { KtorRestaurantsService() }
    val isLoading = remember { mutableStateOf(true) }
    val restaurants = remember { mutableStateOf<List<Restaurant>>(emptyList()) }
    val controller = rememberNavController()

    val context = LocalContext.current
    var currentLocation : Location?= null

    val maxRestNum = 10;

    val granted = remember{
        mutableStateOf(
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            context,
            PERMISSION
        )
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted: Boolean ->
        granted.value = isGranted
    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        if(!granted.value){
            launcher.launch("android.permission.ACCESS_FINE_LOCATION")
        }
        currentLocation = mapsService.getCurrentLocation()
        if(currentLocation != null) {
            restaurants.value =
                restaurantsService.get(
                    currentLocation!!.latitude,
                    currentLocation!!.longitude,
                    maxRestNum,
                    1000
                )
            isLoading.value = false
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }

    if(!isLoading.value) {
        for (item in restaurants.value)
            Log.v("resaturant is:", item.name ?: "None")


        NavHost(
            navController = controller,
            startDestination = "venueNo1"
        ) {
            var actualRestNum = restaurants.value.size
            for(i in 0..actualRestNum - 1){
                composable("venueNo$i") {
                    RestaurantInfo(restaurants.value.get(i),
                        {controller.navigate("venueNo$i"+"details")},
                        { if(i < actualRestNum-1)
                        {controller.navigate("venueNo" + ((i+1).toString())) }
                        else {}},
                        { if(i > 0)
                        {controller.navigate("venueNo"+((i-1).toString())) }
                        else {}})


                }
                composable("venueNo$i" + "details"){
                    RestaurantDetailedInfo(restaurants.value.get(i).id!!)
                }
            }
        }
    }
    else{
        Loader()
    }
}