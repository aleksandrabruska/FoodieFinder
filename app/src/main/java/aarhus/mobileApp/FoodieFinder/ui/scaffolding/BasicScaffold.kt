package aarhus.mobileApp.FoodieFinder.ui.scaffolding

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
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
fun BasicScaffold(sectionName: String, modifier: Modifier = Modifier, backClicked: () -> Unit, content: @Composable () -> Unit){
        Scaffold(
            topBar = {

                TopAppBar(title = { "Foodie Finder scaffold top bar" },
                    navigationIcon = {
                        Row() {
                            IconButton(onClick = {
                                Log.v("aaa", "scaffold icon menu clocked")
                                backClicked()
                            }) {

                                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Menu")
                            }
                            Text(sectionName, fontSize = 34.sp)
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