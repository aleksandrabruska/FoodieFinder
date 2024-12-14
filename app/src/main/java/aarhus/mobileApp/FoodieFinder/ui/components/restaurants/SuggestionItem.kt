package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun SuggestionItem(suggestion: RestaurantFB, userAlreadyVoted: Boolean,
                   vote: (RestaurantFB) -> Unit, restaurantInfo: (String) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row() {
            Column(modifier = Modifier.fillMaxWidth(0.7f).padding(10.dp).clickable {
                Log.v("Clicked", "Clicked")
                restaurantInfo(suggestion.placeID)
            }) {
                Text(suggestion.name, textAlign = TextAlign.Center, fontSize = 25.sp)

                Text("Votes: " + suggestion.number_of_votes.toString(), fontStyle = FontStyle.Italic)
            }
                if (!userAlreadyVoted) {
                    Button(
                        onClick = {
                            vote(suggestion)
                        }, modifier = Modifier
                            .padding(10.dp, 10.dp, 0.dp, 0.dp)
                            .clip(CutCornerShape(20.dp))
                            .height(40.dp)

                    ) { Text("VOTE", fontSize = 10.sp, textAlign = TextAlign.Start) }
                }


        }
}
    }