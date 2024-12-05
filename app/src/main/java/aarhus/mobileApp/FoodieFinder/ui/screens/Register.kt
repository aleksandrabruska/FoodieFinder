package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.domain.Email
import aarhus.mobileApp.FoodieFinder.domain.Password
import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch


@Composable
fun Register() {
    val authService = remember{AuthService()}
    val userService = remember{UserFBService()}
    val scope = rememberCoroutineScope()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val successMessage = remember { mutableStateOf<String?>(null)}


    var email = ""
    var p1 = ""
    var p2 = ""
    var name = ""


    Column() {
        name = inputField("enter nickname", true)
        email = inputField("enter email", true)
        p1 = inputField("Enter your password", false)
        p2 = inputField("Repeat your password", false)

        Button(
            onClick = {
                scope.launch {
                    try {
                        authService.singUp(name, email, p1, p2)


                        successMessage.value = "Registered!"
                        errorMessage.value = null

                    } catch (e: Exception) {
                        errorMessage.value = e.message
                        successMessage.value = null
                    }
                }
            }
        ) {
            Text("Register")
        }

        errorMessage.value?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Red)
        }
        successMessage.value?.let {
            Text(it, color = androidx.compose.ui.graphics.Color.Green)
        }
    }



}