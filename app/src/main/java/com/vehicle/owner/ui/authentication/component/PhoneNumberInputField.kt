package com.vehicle.owner.ui.authentication.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vehicle.owner.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PhoneNumberInputField(modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
    val text = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val change: (String) -> Unit = { it ->
        if (it.length <= 10) {
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
                text = "Enter Phone Number",
                style = MaterialTheme.typography.titleSmall,
                color = primaryColor
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
        prefix = {
            Text(
                text = "+91",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 8.dp),
                color = Color.Black
            )
        },
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
fun PhoneNumberInputFieldPreview() {
    PhoneNumberInputField(onValueChange = {})
}