package aarhus.mobileApp.FoodieFinder.integration.firebase

import aarhus.mobileApp.FoodieFinder.integration.model.User
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun Users() {
    val service = remember{UserService()}
    var models by remember {mutableStateOf<List<User>>(emptyList())}

    LaunchedEffect(key1 = Unit) {
        models = service.getUsers()
    }
    Column {
        //Text("AAAAAAA")
        //Text(models.toString())
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        models.forEach{ User(it) }
    }
}

@Composable
fun User(model: User) {

    Column {
        Row {
            Text("Id:")
            Text(model.id)
        }
        Row {
            Text("Name:")
            Text(model.name)
        }
    }
}
