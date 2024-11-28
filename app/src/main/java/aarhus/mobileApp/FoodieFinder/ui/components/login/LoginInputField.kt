package aarhus.mobileApp.FoodieFinder.ui.components.login

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation


private fun computeLoginInput(text: String) : String {
    val regex = Regex("^[0-9A-Za-z]+$")
    return text.filter { regex.matches(it.toString()) }
}

@Composable
fun loginInputField() : String {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = computeLoginInput(it) },
        label = { Text("Enter login") },
    )
    return text
}