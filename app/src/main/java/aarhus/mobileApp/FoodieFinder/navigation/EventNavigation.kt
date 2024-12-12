package aarhus.mobileApp.FoodieFinder.navigation

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.integration.model.Event
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.components.events.AddEventButton
import aarhus.mobileApp.FoodieFinder.ui.screens.EventView
import aarhus.mobileApp.FoodieFinder.ui.screens.Events.EnterEventScreen
import aarhus.mobileApp.FoodieFinder.ui.screens.Events.UserEvents
import aarhus.mobileApp.FoodieFinder.ui.screens.LogIn
import aarhus.mobileApp.FoodieFinder.ui.screens.MainScreen
import aarhus.mobileApp.FoodieFinder.ui.screens.MyEvents
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
import com.howard.simplemapapp.intergration.google.MapsService
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//TODO - repair loading
//TODO - API call later?
//TODO - repair when wrong password!
private const val PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
@Composable
fun EventNavigation(mapsService: MapsService){
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
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted: Boolean ->
        granted.value = isGranted
    }

    var isLoggedIn by remember { mutableStateOf(false) }


    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            controller.navigate("main_screen")
        } else {
            controller.navigate("login")
        }
        isLoading.value = false
        if(!granted.value){
            launcher.launch("android.permission.ACCESS_FINE_LOCATION")
        }
        currentLocation = mapsService.getCurrentLocation()
        if(currentLocation != null) {
            /*restaurants.value =
                restaurantsService.get(
                    currentLocation!!.latitude,
                    currentLocation!!.longitude,
                    maxRestNum,
                    1000
                )*/
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
            startDestination = "login"
        ) {
            composable("register"){
                Register(
                    navigate = {controller.navigate("login")}
                )
            }
            composable("login"){
                LogIn(
                    navigateToHome = {controller.navigate("main_screen")},
                    login = { email: String, password: String ->
                        scope.launch {
                            try {
                                currentUser.value = authService.logIn(email, password)
                                isLoggedIn = (currentUser.value != null)
                                wrongData.value = !isLoggedIn
                                Log.v("LOGGED IN?", isLoggedIn.toString())
                            } catch (e: Exception) {
                                Log.v("WRONG", e.message.toString())

                                wrongData.value = true

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
                    eventsClicked = {controller.navigate("my_events")}
                )
            }
            composable("add_friend"){
                addFriend(currentUser.value)
            }
            composable("my_friends"){
                MyFriends(
                    currentUser.value,
                    onAddFriendClicked = {controller.navigate("add_friend")},
                    onBackClicked = {controller.navigate("main_screen")}
                )
            }
            composable("my_events"){
                UserEvents(
                    currentUser.value,
                    onBackClicked = {controller.navigate("main_screen")},
                    onAddClicked = {controller.navigate("add_event")},
                    onEnterClicked = { controller.navigate("event_details/$it") })
                //MyEvents({},
                //    onBackClicked = {controller.navigate("main_screen")}, action = {})
            }
            composable("add_event"){
                /*val user = remember { mutableStateOf<UserFB?>(null) }
                val authService = remember{ AuthService() }
                LaunchedEffect(key1 = Unit) {
                    // TODO HARD CODED
                    user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")
                    Log.v("Null?", (user.value == null).toString())
                }*/
                //Log.v("Null?", (user.value == null).toString())
                currentUser.value?.let {AddEventButton(currentUser.value!!)}

            }
            composable("event_details/{id}"){
                val id = it.arguments?.getString("id") ?: ""
                /*val user = remember { mutableStateOf<UserFB?>(null) }
                val authService = remember{ AuthService() }
                LaunchedEffect(key1 = Unit) {
                    // TODO HARD CODED
                        user.value = authService.logIn("ola@gmail.pl", "aaaaaaaa")
                }*/
                EnterEventScreen(id, currentUser.value)

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