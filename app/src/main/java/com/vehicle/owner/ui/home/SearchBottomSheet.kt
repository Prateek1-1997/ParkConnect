package com.vehicle.owner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vehicle.owner.R
import com.vehicle.owner.ui.search.component.SuggestionItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    content: @Composable () -> (Unit),
    modifier: Modifier = Modifier,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()


    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {},
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        ) {
            IconButton(
                onClick = onDismiss,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_splash_logo),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                },
            )
        }
        ///Users/abhisek/AndroidStudioProjects/whoIsCar/app/src/main/res/drawable/ic_splash_logo.xml
        content()
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, widthDp = 420, heightDp = 1000)
@Composable
fun BottomSheetPreview() {
    BottomSheet(
        onDismiss = {},
        content = {},
    )
}

@Composable
fun BottomSheetContent(
    uiState: HomeUiState,
    onSearch: (String) -> Unit,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Search")
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(query = "") {
            onSearch(it)
        }
        when (uiState) {
            is HomeUiState.showData -> {
                if (uiState.vehicleNumber.isNotEmpty()) {
                    SuggestionItem(vehicleNo = uiState.vehicleNumber, onClick = onItemClick)
                } else {
                    EmptyScreen()
                }

            }

            else -> {

            }

        }


    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No results found",
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEmptyScreen() {
    EmptyScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf(query) }

    val imeActionHandler = rememberUpdatedState(onSearch)
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = { newValue ->
            text = newValue
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
        placeholder = {
            Text("Enter Vehicle Number")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.Black,
            containerColor = Color.LightGray,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                imeActionHandler.value(text)
            }
        )
    )
}

@Preview
@Composable
private fun serachView() {
    SearchBar(query = "") {

    }
}

