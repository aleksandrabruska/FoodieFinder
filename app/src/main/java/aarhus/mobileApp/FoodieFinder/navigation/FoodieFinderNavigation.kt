package aarhus.mobileApp.FoodieFinder.navigation

import aarhus.mobileApp.FoodieFinder.integration.googlePlaces.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.screens.AddEvent
import aarhus.mobileApp.FoodieFinder.ui.screens.Events.EventInfo
import aarhus.mobileApp.FoodieFinder.ui.screens.Events.MyEvents

import aarhus.mobileApp.FoodieFinder.ui.screens.LogIn
import aarhus.mobileApp.FoodieFinder.ui.screens.MainScreen

import aarhus.mobileApp.FoodieFinder.ui.screens.MyFriends
import aarhus.mobileApp.FoodieFinder.ui.screens.Register
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
import aarhus.mobileApp.FoodieFinder.integration.googleMaps.MapsService
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
@Composable
fun FoodieFinderNavigation(mapsService: MapsService){
    val restaurantsService = remember { KtorRestaurantsService() }
    val isLoading = remember { mutableStateOf(false) }
    val restaurants = remember { mutableStateOf<List<Restaurant>>(emptyList()) }
    val controller = rememberNavController()
    var currentUser = remember { mutableStateOf<UserFB?>(null) }
    val context = LocalContext.current
    var currentLocation : Location?= null

    val maxRestNum = 10;

    val authService = remember{ AuthService() }
    val scope = rememberCoroutineScope()

    var wrongData = remember{ mutableStateOf(false)}

    val granted = remember{
        mutableStateOf(
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            context,
            PERMISSION
        )
        )
    }
    val locationManager = remember{ context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    var enabled = remember{mutableStateOf(false)}
    enabled.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted: Boolean ->
        granted.value = isGranted
    }

    var isLoggedIn by remember { mutableStateOf(false) }


    LaunchedEffect(isLoggedIn, enabled) {
        if(!granted.value){
            launcher.launch("android.permission.ACCESS_FINE_LOCATION")
        }
        else if(enabled.value) {
            currentLocation = mapsService.getCurrentLocation()
        }
            if (isLoggedIn) {
            controller.navigate("main_screen")
        } else {
            if((controller.currentDestination?.route) != "login") {
                controller.navigate("login")
            }
        }
        isLoading.value = false

    }

    DisposableEffect(Unit) {
        onDispose {
            restaurantsService.close()
        }
    }

        for (item in restaurants.value)
            Log.v("resaturant is:", item.name ?: "None")


        NavHost(
            navController = controller,
            startDestination = "login"
        ) {
            composable("register"){
                Register(
                    navigate = {controller.navigate("login")}
                )
            }
            composable("login"){
                LogIn(
                    createAccount = {controller.navigate("register")},
                    login = { email: String, password: String, loading: MutableState<Boolean> ->
                        loading.value = true;
                        scope.launch {
                            try {
                                currentUser.value = authService.logIn(email, password)
                                isLoggedIn = (currentUser.value != null)
                                wrongData.value = !isLoggedIn
                                Log.v("LOGGED IN?", isLoggedIn.toString())
                                loading.value = false;

                            } catch (e: Exception) {
                                Log.v("WRONG", e.message.toString())

                                wrongData.value = true
                                loading.value = false;
                                controller.navigate("login")

                            }
                        }
                    },


                    wrongData = {
                        Log.v("WRONG DATA?", wrongData.value.toString())
                        wrongData.value
                    }


                )
            }
            composable("main_screen"){
                MainScreen(
                    friendsClicked = {controller.navigate("my_friends")},
                    eventsClicked = {controller.navigate("my_events")},
                    onLogOut = {isLoggedIn = false; currentUser.value = null}
                )
            }
            composable("add_friend"){
                addFriend(currentUser.value,
                    onBackClicked = {
                        controller.navigate("my_friends")})
            }
            composable("my_friends"){
                MyFriends(
                    currentUser.value,
                    onAddFriendClicked = {controller.navigate("add_friend")},
                    onBackClicked = {controller.navigate("main_screen")}
                )
            }
            composable("my_events"){
                    MyEvents(
                    currentUser.value,
                    onBackClicked = {controller.navigate("main_screen")},
                    onAddClicked = {controller.navigate("add_event")},
                    onEnterClicked = { isLoading.value = true; controller.navigate("event_details/$it/0/0") })
            }
            composable("add_event"){
                currentUser.value?.let {AddEvent(currentUser.value!!,
                    onBackClicked = {controller.navigate("my_events")})}

            }
            composable("event_details/{id}/{venueChosenID}/{name}"){
                val id = it.arguments?.getString("id") ?: ""
                val venueChosen = (it.arguments?.getString("venueChosenID") ?: "0")
                val name = it.arguments?.getString("name") ?: ""
                EventInfo(id, currentUser.value, venueChosen, name,
                    addRestaurantClicked = {controller.navigate("venue/0/$id")},
                    backClicked = {controller.navigate("my_events")},
                    restaurantInfo = {id: String -> controller.navigate("venueDetails/$id")})
            }
            composable("venue/{num}/{eventID}") {
                var locationEnabled = enabled.value
                if(locationEnabled) {
                    LaunchedEffect(key1 = Unit) {
                        isLoading.value = true
                        if (currentLocation != null) {
                            restaurants.value =
                                restaurantsService.get(
                                    currentLocation!!.latitude,
                                    currentLocation!!.longitude,
                                    maxRestNum,
                                    1000
                                )
                        }
                        isLoading.value = false
                    }
                    if (isLoading.value == false) {
                        var actualRestNum = restaurants.value.size
                        val num = (it.arguments?.getString("num") ?: "0").toInt()
                        val event = (it.arguments?.getString("eventID") ?: "0")
                        val next: Int = (if (num < actualRestNum - 1) (num + 1) else 0)
                        val prev: Int = (if (num > 0) (num - 1) else (actualRestNum - 1))
                        val venueID = restaurants.value.get(num).id
                        Log.v("Next", next.toString())
                        Log.v("Prev", prev.toString())
                        RestaurantInfo(restaurants.value.get(num),
                            details = { controller.navigate("venueDetails/$venueID") },
                            navigate = { controller.navigate("venue/$next/$event") },
                            navigateBack = { controller.navigate("venue/$prev/$event") },
                            check = {
                                controller.navigate(
                                    "event_details/$event/${
                                        restaurants.value.get(
                                            num
                                        ).id
                                    }/${
                                        restaurants.value.get(
                                            num
                                        ).name
                                    }"
                                )
                            },
                            backHandler = { controller.navigate("my_events") })

                    }
                }
                else{
                    Column() {
                        Text("Turn on your location and run the application again", fontSize = 30.sp)
                    }
                }
            }

            composable("venueDetails/{id}"){
                //val num = (it.arguments?.getString("num") ?: "0").toInt()
                val id = (it.arguments?.getString("id") ?: "0")
                //RestaurantDetailedInfo(restaurants.value.get(num).id!!)
                RestaurantDetailedInfo(id)
            }
        }
}