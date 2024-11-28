package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.ui.components.login.loginInputField
import aarhus.mobileApp.FoodieFinder.ui.components.login.passwordInputField
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview




@Composable
fun LogIn() {
    var login = ""
    var password = ""

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
            password = passwordInputField()
        }
        Row() {
            Button(onClick = { Log.v("LOGIN", "login:" + login + " password:" + password) }) {
                Text("Log in!")
            }
        }
    }

}

@Preview
@Composable
fun AAAA() {
    LogIn()
}