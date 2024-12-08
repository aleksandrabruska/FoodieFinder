package aarhus.mobileApp.FoodieFinder.ui.scaffolding

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsEventsScaffold(text: String, modifier: Modifier = Modifier, addClicked: () -> Unit, backClicked: () -> Unit, content: @Composable () -> Unit){
        Scaffold(
            topBar = {
                TopAppBar(title = { "Foodie Finder scaffold top bar" },
                    navigationIcon = {
                        Row() {
                            IconButton(onClick = {
                                backClicked()
                            }) {

                                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Menu")
                            }
                            Text(text, fontSize = 34.sp)
                        }
                    }
                )
            },
            bottomBar = {

                BottomAppBar(

                    actions = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(onClick = addClicked) {
                                Icon(Icons.Default.Add, contentDescription = "add")
                            }
                        }
                    }


                )

            }

                )
        { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }

}