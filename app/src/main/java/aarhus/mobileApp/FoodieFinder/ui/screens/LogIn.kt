package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch



@Composable
fun LogIn(navigateToHome: () -> Unit, login: (String, String) -> Unit, wrongData: () -> Boolean) {
    val email = remember { mutableStateOf<String>("") }
    val password = remember { mutableStateOf<String>("") }

    val errorMessage = remember { mutableStateOf<String?>(null) }
    val successMessage = remember { mutableStateOf<String?>(null) }
   // val user = remember {mutableStateOf<UserFB?>(null)}
   // val scope = rememberCoroutineScope()

    //val authService = remember{ AuthService() }


    Column() {
        inputField("enter email", true, email)
        inputField("Enter your password", false, password)

        Button(

            onClick = {
               // scope.launch {
                    //try {

                        //user.value = authService.logIn(email.value, password.value)
                        /*user.value = */

                    var success = login(email.value, password.value)





                        //if(user.value != null) {
                        //    successMessage.value = "Logged in!"
                        //    errorMessage.value = null
                        //}
                        //else{
                        //    throw Exception("Exception!")
                        //}
                    //}
                    //catch (e: Exception) {
                    //    errorMessage.value = e.message
                     //   successMessage.value = null
                   // }

                //}
            }) {
            Text("Log in!")
        }
        if (!wrongData()) {
            successMessage.value = ""
            errorMessage.value = null
        } else {
            successMessage.value = null
            errorMessage.value = "Failed to log in. Wrong email or password."
        }

        errorMessage.value?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Red)
        }
        successMessage.value?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Green)
        }

        //user.value?.let {
            //Text("USERNAME: " + user.value!!.name)
            //Text("EMAIL: " + user.value!!.email)
            //Text("USER ID:" + user.value!!.id)
            //navigateToHome()
        ///}
    }
}

@Composable
fun Loggg() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = "logInScreen"
    ) {
        composable("logInScreen") {

        }

        composable("register") {

        }

        composable ("home") {

        }
    }
}
