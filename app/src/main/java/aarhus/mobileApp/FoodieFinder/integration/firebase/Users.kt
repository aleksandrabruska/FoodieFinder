package aarhus.mobileApp.FoodieFinder.integration.firebase

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import kotlin.random.Random

@Composable
fun Users() {
    val service = remember{ UserFBService() }
    var models by remember { mutableStateOf<List<UserFB>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    LaunchedEffect(key1 = Unit) {
        models = service.getUsers()
        isLoading.value = false
    }
    if(isLoading.value == false) {
        Column {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                models.forEach {
                    User(it)
                    printFriends(models, it)
                }

            }



        }



    }

    /*var name by remember { mutableStateOf<String>("")}
    var email by remember { mutableStateOf<String>("")}

    var f1 by remember { mutableStateOf<String>("")}
    var f2 by remember { mutableStateOf<String>("")}


    LaunchedEffect(key1 = Unit) {
        models = service.getUsers()
    }
    Column {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            models.forEach{
                User(it)
                PrintFriends(it, service)
            }

        }

        name = inputField("enter name", true)
        email = inputField("enter email", true)
        f1 = inputField("add friend 1", true)
        f2 = inputField("add friend 2", true)

        Button(
            onClick = {
                val a = UserFB(
                    name,
                    email,
                    arrayListOf(f1, f2)
                )
                service.saveUser(a)

            }
        ) {
            Text("ADD FRIEND")
        }


    }*/
}

@Composable
fun User(model: UserFB) {
    Column {
        Text("name: " + model.name)
        Text("email: " + model.email)
        model.friends.forEach{Text(it)}
    }
}

@Composable
fun printFriends(users: List<UserFB>, mainUser: UserFB) {
    Column {
        users.forEach {
            if (mainUser.friends.contains(it.name)) {
                Text("USER " + mainUser.name + " HAS A FRIEND " + it.name)
            }
        }
    }
}

@Composable
fun PrintFriends(model: UserFB, service: UserFBService) {
    val friendsNames = remember { mutableStateOf<List<String>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        model.friends.forEach {
            val a = service.getHorse(it)
            if (a != null)
                friendsNames.value = friendsNames.value + a!!.name
        }
        isLoading.value = false
    }



    if(isLoading.value == false){
    Column {
        friendsNames.value.forEach { name ->
            Log.v("Friend!!!", name)
            Text(text = name)
        }
    }}
}