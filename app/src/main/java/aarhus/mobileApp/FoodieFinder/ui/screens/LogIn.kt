package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
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
            login = inputField("enter login")

        }
        Row() {
            password = inputField("Enter your password")
        }
        Row() {
            Button(onClick = { Log.v("LOGIN", "login:" + login + " password:" + password) }) {
                Text("Log in!")
            }
        }
        Row() {
            Column() {
                Text("Don't have an account?")
            }
            Column() {
                Button(onClick ={Log.v ("LOGIN", "register")}) {
                    Text("Register!")
                }
            }
        }
    }

}
