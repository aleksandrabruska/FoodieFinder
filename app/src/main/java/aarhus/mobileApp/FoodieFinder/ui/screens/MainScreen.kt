package aarhus.mobileApp.FoodieFinder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(friendsClicked: () -> Unit, eventsClicked:  () -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally){

        Text("Foodie Finder", textAlign = TextAlign.Center, fontSize = 50.sp,
            modifier = Modifier.padding(40.dp))
        Row() {
            val image =
                painterResource(aarhus.mobileApp.FoodieFinder.R.drawable.logo)
            Image(

                painter = image,

                contentDescription = "Plant Animation",
                modifier = Modifier.padding(30.dp).width(600.dp)

            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = friendsClicked, modifier = Modifier.clip(
            CutCornerShape(30.dp)).fillMaxWidth(0.8f).height(70.dp)){
            Text("Friends",textAlign = TextAlign.Center, fontSize = 35.sp)
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = eventsClicked, modifier = Modifier.clip(
            CutCornerShape(30.dp)).fillMaxWidth(0.8f).height(70.dp)){
            Text("Events",textAlign = TextAlign.Center, fontSize = 35.sp)
        }
    }

}