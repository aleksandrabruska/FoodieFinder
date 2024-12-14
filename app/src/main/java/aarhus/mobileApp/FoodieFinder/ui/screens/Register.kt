package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.domain.Email
import aarhus.mobileApp.FoodieFinder.domain.Password
import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun Register(navigate: () -> Unit) {
    val authService = remember{AuthService()}
    val userService = remember{UserFBService()}
    val scope = rememberCoroutineScope()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val successMessage = remember { mutableStateOf<String?>(null)}
    val isLoading = remember { mutableStateOf(false) }

    val email = remember { mutableStateOf<String>("") }
    val p1 = remember { mutableStateOf<String>("") }
    val p2 = remember { mutableStateOf<String>("") }
    val name = remember { mutableStateOf<String>("") }


    Column(horizontalAlignment =  Alignment.CenterHorizontally) {
        Text("Foodie Finder", textAlign = TextAlign.Center, fontSize = 50.sp,
            modifier = Modifier.padding(40.dp))
        Text("Sign up to start your journey!", fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(20.dp))
        OutlinedCard(
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {

                inputField("enter nickname", true, name)
                inputField("enter email", true, email)
                inputField("Enter your password", false, p1)
                inputField("Repeat your password", false, p2)

                Spacer(modifier = Modifier.padding(20.dp))

                Button(
                    onClick = {
                        isLoading.value = true
                        scope.launch {
                            try {
                                authService.singUp(name.value, email.value, p1.value, p2.value)


                                successMessage.value = "Registered!"
                                errorMessage.value = null
                                isLoading.value = false
                                navigate()

                            } catch (e: Exception) {
                                errorMessage.value = e.message
                                isLoading.value = false
                                successMessage.value = null
                            }
                        }
                    }, modifier = Modifier
                        .clip(CutCornerShape(20.dp))
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text("Register")
                }
            }
        }
        if(!isLoading.value) {
            errorMessage.value?.let {
                Text(it, color = androidx.compose.ui.graphics.Color.Red)
            }
            successMessage.value?.let {
                Text(it, color = androidx.compose.ui.graphics.Color.Green)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Box(Modifier.clickable { navigate() }) {
                Text("Already have an account? Click here to sign in!")
            }
        }
        else{
            Loader()
        }
    }



}