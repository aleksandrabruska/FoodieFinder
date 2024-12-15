package aarhus.mobileApp.FoodieFinder.ui.components.events.restaurants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewSugestion(suggestion: String, onSaveClicked: () -> Unit){
    Box(    //to center the whole thing
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .padding(10.dp)) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .fillMaxWidth(0.9f)
            )
            {

                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Your suggestion", textAlign = TextAlign.Center, fontSize = 25.sp)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row() {
                            Column(modifier = Modifier.fillMaxWidth(0.7f).padding(10.dp)) {
                                Text(suggestion, textAlign = TextAlign.Center, fontSize = 25.sp)
                            }
                            Button(
                                onClick = {
                                    onSaveClicked()
                                }, modifier = Modifier
                                    .padding(10.dp, 10.dp, 0.dp, 0.dp)
                                    .clip(CutCornerShape(20.dp))
                                    .height(40.dp)

                            ) { Text("SAVE", fontSize = 10.sp, textAlign = TextAlign.Start) }
                        }
                    }
                }
            }
        }
    }

}