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

private fun computePasswordInput(text: String) : String {
    val regex = Regex("^[0-9A-Za-z!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>?/|]+$")
    return text.filter { regex.matches(it.toString()) }
}

@Composable
fun passwordInputField() : String {
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = { password = computePasswordInput(it) },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation()
    )
    return password
}