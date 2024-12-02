package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.logInUser
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser



@Composable
fun UserDetails(user: FirebaseUser) {
    val email = user.email
    val uid = user.uid
    Column {
        Text(email.toString())
        Text(uid.toString())
    }

}

@Composable
fun LogIn() {
    var email = ""
    var password = ""

    val errorMessage = remember { mutableStateOf<String?>(null) }
    val successMessage = remember { mutableStateOf<String?>(null) }

    val user = remember {mutableStateOf<FirebaseUser?>(null)}

    Column() {
        email = inputField("enter email", true)
        password = inputField("Enter your password", false)

        Button(
            onClick = {
                // result is a user
                logInUser(email, password) { result ->
                    result.onSuccess { loggedInUser ->
                        errorMessage.value = null
                        successMessage.value = "Successfully logged in!"
                        user.value = loggedInUser

                    }.onFailure { exception ->
                        errorMessage.value = exception.message
                        successMessage.value = null
                    }
                }

            }) {
            Text("Log in!")
        }

        errorMessage.value?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Red)
        }
        successMessage.value?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Green)
        }
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
