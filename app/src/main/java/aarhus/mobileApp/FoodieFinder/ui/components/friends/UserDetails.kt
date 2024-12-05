package aarhus.mobileApp.FoodieFinder.ui.components.friends

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserDetails(user: UserFB) {
    Text(user.id)
    Text(user.email)
    Text(user.name)
}