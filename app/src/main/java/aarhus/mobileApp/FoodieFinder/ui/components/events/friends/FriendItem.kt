package aarhus.mobileApp.FoodieFinder.ui.components.events.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.ui.components.events.DeleteButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun FriendItem(removable: Boolean = true, friend: UserFB,onDelete: () -> Unit = {}){
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(20.dp, 10.dp), verticalAlignment = Alignment.Bottom){
            Text(friend.name,  textAlign = TextAlign.Left, fontSize = 25.sp, modifier = Modifier.fillMaxWidth(0.8f))
            //ManageFriendButton(" X ", action = {
            if(removable){
                DeleteButton( action = {
                    onDelete()
                })
            }

        }
    }
}