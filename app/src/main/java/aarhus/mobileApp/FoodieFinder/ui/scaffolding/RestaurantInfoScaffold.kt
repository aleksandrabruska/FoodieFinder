package aarhus.mobileApp.FoodieFinder.ui.scaffolding

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantInfoScaffold(modifier: Modifier = Modifier,
                           checkClicled: ()-> Unit, locationClicked: () -> Unit,
                           infoClicked: () -> Unit, arrowClicked: () -> Unit,
                           backClicked: () -> Unit,
                           content: @Composable () -> Unit){
//TODO: Implement arrow back
        Scaffold(
            bottomBar = {

                BottomAppBar(

                    actions = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(onClick = backClicked) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "prev")
                            }
                            IconButton(onClick = checkClicled) {
                                Icon(Icons.Filled.Check, contentDescription = "choose")
                            }
                            IconButton(onClick = locationClicked) {
                                Icon(Icons.Filled.LocationOn, contentDescription = "location")
                            }
                            IconButton(onClick = infoClicked) {
                                Icon(Icons.Filled.Info, contentDescription = "info")
                            }
                            IconButton(onClick = arrowClicked) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "next"
                                )
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