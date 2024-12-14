package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
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
fun SuggestionsList(suggestions: List<RestaurantFB>, thisUserAlreadyVoted: MutableState<Boolean>,
                    thisUserAlreadyPosted: MutableState<Boolean>,
                    onAddClicked: () -> Unit, vote: (RestaurantFB) -> Unit,
                    restaurantInfo: (String) -> Unit){
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

                Column(modifier = Modifier.padding(10.dp)) {
                    Text("Suggested restaurants", textAlign = TextAlign.Center, fontSize = 25.sp)
                    Spacer(modifier = Modifier.padding(10.dp))
                    for (suggestion in suggestions) {
                        SuggestionItem(suggestion, thisUserAlreadyVoted.value, vote, restaurantInfo)
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                    if (!thisUserAlreadyPosted.value) {
                        Button(onAddClicked,
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
                            modifier = Modifier
                            .padding(10.dp, 10.dp, 0.dp, 0.dp)
                            .clip(CutCornerShape(20.dp))
                            .height(40.dp)){
                            Text("+", textAlign = TextAlign.Center, fontSize = 25.sp)
                        }
                    }
                }
            }
        }
    }
}