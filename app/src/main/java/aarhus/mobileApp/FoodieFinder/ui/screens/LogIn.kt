package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.integration.firebase.auth.AuthService
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.ui.components.Loader
import aarhus.mobileApp.FoodieFinder.ui.components.login.inputField
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch



@Composable
fun LogIn(createAccount: () -> Unit, login: (String, String, MutableState<Boolean> ) -> Unit,
          wrongData: () -> Boolean) {
    val email = remember { mutableStateOf<String>("") }
    val password = remember { mutableStateOf<String>("") }

    val errorMessage = remember { mutableStateOf<String?>(null) }
    val successMessage = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Foodie\n\nFinder", textAlign = TextAlign.Center, fontSize = 50.sp,
            modifier = Modifier.padding(40.dp))
        Text("Sign in to start your journey!", fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(20.dp))
        OutlinedCard(
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                inputField("enter email", true, email)
                Spacer(modifier = Modifier.padding(10.dp))
                inputField("Enter your password", false, password)
                Spacer(modifier = Modifier.padding(20.dp))
                Button(

                    onClick = {
                        var success = login(email.value, password.value, isLoading)
                    }, modifier = Modifier
                        .clip(CutCornerShape(20.dp))
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text("Log in!", textAlign = TextAlign.Center, fontSize = 25.sp)
                }
            }
        }


        if (!wrongData()) {
            successMessage.value = ""
            errorMessage.value = null
        } else {
            successMessage.value = null
            errorMessage.value = "Failed to log in. Wrong email or password."
        }
        if(!isLoading.value) {
            errorMessage.value?.let {
                Text(it, color = androidx.compose.ui.graphics.Color.Red)
            }
            successMessage.value?.let {
                Text(it, color = androidx.compose.ui.graphics.Color.Green)
            }
        }
        else{
            Loader()
        }
        Box(Modifier.clickable { createAccount() }){
            Text("Don't have an account? Click here to sign up!")
        }
    }
}
