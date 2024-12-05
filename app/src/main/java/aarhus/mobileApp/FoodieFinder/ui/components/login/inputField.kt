package aarhus.mobileApp.FoodieFinder.ui.components.login

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation



@Composable
fun inputField(placeH: String, visible: Boolean, text: MutableState<String>) : String {

    OutlinedTextField(
        value = text.value,
        onValueChange = { input ->
            text.value = input.filter {
                !it.isWhitespace()
            }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),

        label = { Text(placeH) },
    )
    return text.value
}