package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.domain.Email
import aarhus.mobileApp.FoodieFinder.domain.Password
import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.signUpUser
import aarhus.mobileApp.FoodieFinder.ui.components.login.checkPassword
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember


@Composable
fun Register() {
    var em = ""
    var p1 = ""
    var p2 = ""
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val successMessage = remember { mutableStateOf<String?>(null)}


    Column() {
        em = inputField("enter email", true)
        p1 = inputField("Enter your password", false)
        p2 = inputField("Repeat your password", false)

        Button(
            onClick = {
                try {
                    checkPassword(p1, p2)
                    val email = Email(em)
                    val password = Password(p1)
                    Log.v("REG", "email and pass ok")

                    signUpUser(email.email, password.password) { result ->
                        result.onSuccess {
                            errorMessage.value = null
                            successMessage.value = "Successfully registered!"

                        }.onFailure { exception ->
                            errorMessage.value = exception.message
                            successMessage.value = null
                        }
                    }
                }
                catch (e: Exception) {
                    errorMessage.value = e.message
                    successMessage.value = null
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