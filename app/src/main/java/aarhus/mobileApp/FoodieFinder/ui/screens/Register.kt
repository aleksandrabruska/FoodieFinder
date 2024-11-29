package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.ui.components.login.checkPassword
import aarhus.mobileApp.FoodieFinder.ui.components.login.loginInputField
import aarhus.mobileApp.FoodieFinder.ui.components.login.passwordInputField
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember


@Composable
fun Register() {
    var login = ""
    var password = ""
    var repeat_password = ""

    var res by remember { mutableStateOf<String?>(null) }

    Column() {
        Row() {
            Text("Welcome to")
        }
        Row() {
            Text("Foodie finder")
        }
        Row() {
            login = loginInputField()

        }
        Row() {
            password = passwordInputField("Enter your password")
        }
        Row() {
            repeat_password = passwordInputField("Repeat your password")
        }
        Row() {
            Button(
                onClick = {
                    res = checkPassword(password, repeat_password)
                    res?.let { Log.v("reg", it) }

                }
            ) {
                Text("Register")
            }
        }
        Row() {
            res?.let {Text(it)}
        }




    }

}