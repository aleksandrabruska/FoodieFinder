package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuggestionsList(suggestions: List<RestaurantFB>, thisUserAlreadyVoted: MutableState<Boolean>, thisUserAlreadyPosted: MutableState<Boolean>, onAddClicked: () -> Unit, vote: (RestaurantFB) -> Unit){
    Box(    //to center the whole thing
        //modifier = Modifier.fillMaxSize(),
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

                Column(modifier = Modifier.padding(25.dp)) {
                    Text("Suggested restaurants", textAlign = TextAlign.Center, fontSize = 25.sp)
                    Spacer(modifier = Modifier.padding(10.dp))
                    for (suggestion in suggestions) {
                        Text(suggestion.name, textAlign = TextAlign.Center, fontSize = 18.sp)
                        Text("NUMBER OF VOTES: " + suggestion.number_of_votes.toString())
                        if (!thisUserAlreadyPosted.value) {
                            Button( onClick = {
                                vote(suggestion)
                            }
                            ) {Text("VOTE")}
                        }
                    }
                    if (!thisUserAlreadyVoted.value) {
                        Button(onAddClicked, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface)){
                            Text("+", textAlign = TextAlign.Center, fontSize = 25.sp)
                        }
                    }
                }
            }
        }
    }
}