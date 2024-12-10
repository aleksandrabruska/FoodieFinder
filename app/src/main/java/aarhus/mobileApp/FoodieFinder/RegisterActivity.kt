package aarhus.mobileApp.FoodieFinder

import aarhus.mobileApp.FoodieFinder.ui.screens.LogIn
import aarhus.mobileApp.FoodieFinder.ui.screens.Register
import aarhus.mobileApp.FoodieFinder.ui.theme.FoodieFinderTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp

class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            FoodieFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        //Register()

                    }
                }
            }
        }
    }
}