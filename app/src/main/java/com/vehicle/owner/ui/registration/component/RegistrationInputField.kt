package com.vehicle.owner.ui.registration.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import com.vehicle.owner.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegistrationInputField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    maxLength: Int? = null,
) {
    val text = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val change: (String) -> Unit = { it ->
        if (it.length <= (maxLength ?: Int.MAX_VALUE)) {
            text.value = it
            onValueChange.invoke(text.value)
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text.value, onValueChange = change,
        modifier = modifier
            .background(Color.White),
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.titleSmall,
                color = primaryColor
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Characters,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
        textStyle = MaterialTheme.typography.titleMedium,
        isError = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = primaryColor,
            cursorColor = primaryColor,
            focusedTextColor = Color.Black,
        ),
        interactionSource = interactionSource,
    )
}

@Preview
@Composable
fun RegistrationInputFieldPreview() {
    RegistrationInputField(onValueChange = {}, placeholderText = "Enter Your Name")
}