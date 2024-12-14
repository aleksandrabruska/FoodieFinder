package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.KtorRestaurantsService
import aarhus.mobileApp.FoodieFinder.integration.model.Comment
import aarhus.mobileApp.FoodieFinder.integration.model.Restaurant
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailedRestaurantInfoBox(restaurant_: Restaurant){

    val restaurant = remember { mutableStateOf<Restaurant>(restaurant_) }
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally){

        Spacer(modifier = Modifier.height(20.dp))
        Text(restaurant.value.summary?.let { it } ?: "", textAlign = TextAlign.Center, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp))

        if(restaurant.value.openingHours?.size != 0) {
            Box(Modifier.border(BorderStroke(1.dp, Color.Black))) {
                Column(Modifier.padding(20.dp)) {
                    Text("Opening hours:", textAlign = TextAlign.Center, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    for (i in 1..<(restaurant.value.openingHours?.size ?: 0)) {
                        Text(restaurant.value.openingHours?.elementAt(i).let { it.toString() }
                            ?: "None",
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Reviews:", textAlign = TextAlign.Center, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp))
        for(comment in restaurant.value.comments?: emptyList()){
            Text(comment.text, textAlign = TextAlign.Center, fontSize = 18.sp, fontStyle = FontStyle.Italic)
            Text("[author: " + comment.author + "]", textAlign = TextAlign.Center, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
        }
        Text(("Find more information on " + restaurant.value.website?.let { it.toString() } )?: "None", textAlign = TextAlign.Center, fontSize = 15.sp)
    }


}

@Preview(showBackground = true)
@Composable
fun pr() {
    val restaurant = Restaurant("123", "SomeName", 2.1, 123, "" +
            "Stenadelverej 12131", 2, "www.website.com", "This is restaurant this is restaurant" +
            "this is restaurnat", null, Array(7, {"Monday 12-12"}),0.0,0.0,
        List<Comment>(3, { Comment("nick123", "Dolorum aut est optio mollitia et. Et nobis vitae aperiam quisquam assumenda. Vel facilis dicta autem. Ut atque rerum provident et") }) )
    DetailedRestaurantInfoBox(restaurant)
}