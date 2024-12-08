package aarhus.mobileApp.FoodieFinder.navigation

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.screens.EventView
import aarhus.mobileApp.FoodieFinder.ui.screens.MainScreen
import aarhus.mobileApp.FoodieFinder.ui.screens.MyEvents
import aarhus.mobileApp.FoodieFinder.ui.screens.MyFriends
import aarhus.mobileApp.FoodieFinder.ui.screens.RestaurantDetailedInfo
import aarhus.mobileApp.FoodieFinder.ui.screens.RestaurantInfo
import aarhus.mobileApp.FoodieFinder.ui.screens.addFriend
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
//TODO - API call later?
private const val PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
@Composable
fun EventNavigation(mapsService: MapsService){
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
            startDestination = "main_screen"
        ) {
            composable("main_screen"){
                MainScreen(
                    friendsClicked = {controller.navigate("my_friends")},
                    eventsClicked = {controller.navigate("my_events")}
                )
            }
            composable("add_friend"){
                addFriend()
            }
            composable("my_friends"){
                MyFriends(
                    onAddFriendClicked = {controller.navigate("add_friend")},
                    onBackClicked = {controller.navigate("main_screen")}
                )
            }
            composable("my_events"){
                MyEvents({},
                    onBackClicked = {controller.navigate("main_screen")})
            }

            composable("event/{id}"){
                var event = Event("Mary birthday", emptyList(), emptyList())
                EventView(event, {controller.navigate("venue/0")},
                    {controller.navigate("my_events")})
            }
            composable("venue/{num}") {
                var actualRestNum = restaurants.value.size
                val num = (it.arguments?.getString("num") ?: "0").toInt()
                val next: Int = (if(num < actualRestNum-1) (num+1) else 0)
                val prev: Int = (if(num > 0) (num-1) else (actualRestNum-1))
                    Log.v("Next", next.toString())
                Log.v("Prev", prev.toString())
                RestaurantInfo(restaurants.value.get(num),
                        {controller.navigate("venueDetails/$num")},
                        {controller.navigate("venue/$next")},
                        {controller.navigate("venue/$prev")},
                        {controller.navigate("event/0")},
                        {controller.navigate("event/0")})


            }

            composable("venueDetails/{num}"){
                val num = (it.arguments?.getString("num") ?: "0").toInt()
                RestaurantDetailedInfo(restaurants.value.get(num).id!!)
            }
        }
    }
    else{
        Loader()
    }
}