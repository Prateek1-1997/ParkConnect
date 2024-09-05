package com.vehicle.owner.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicle.owner.R
import com.vehicle.owner.core.components.TraditionalToolbar
import com.vehicle.owner.core.extension.getTimeFormatted
import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.ui.chat.data.Person
import com.vehicle.owner.ui.theme.LightYellow
import com.vehicle.owner.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChatScreen(
    data: Person,
    uiEvent: (ChatUiIntent) -> Unit,
    uiState: ChatUiState,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {
    var message by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TraditionalToolbar(scrollBehavior, onBack, "${data.name}")
        },
        snackbarHost = {
            SnackbarHost(hostState = SnackbarHostState())
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (uiState.chatHistory.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            top = 25.dp,
                            end = 15.dp,
                            bottom = 50.dp
                        )
                    ) {
                        items(uiState.chatHistory, key = { it.timeStamp }) {
                            ChatRow(chat = it)
                        }
                    }
                }
            }

            CustomTextField(
                text = message, onValueChange = { message = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .align(BottomCenter),
                onSend = {
                    uiEvent.invoke(ChatUiIntent.SendMessage(message))
                    message = ""
                }
            )
        }
    }
}

@Composable
fun ChatRow(
    chat: ChatModel
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (chat.direction.not()) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (chat.direction) primaryColor else LightYellow
                ),
            contentAlignment = Center
        ) {
            Text(
                text = chat.message,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if (chat.direction) White else Black,
                ),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                textAlign = TextAlign.End,
            )
        }
        Text(
            text = getTimeFormatted(chat.timeStamp),
            style = MaterialTheme.typography.labelSmall.copy(
                color = Gray,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
) {
    TextField(
        value = text, onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.type_message),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Black
                ),
                textAlign = TextAlign.Center
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = LightGray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_send_24), contentDescription = "",
                tint = Black,
                modifier = Modifier
                    .size(15.dp)
                    .clickable { onSend() }
            )
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth(),
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChatScreenPreview(
) {
    ChatScreen(
        Person(id = "1", name = "Suraj", icon = R.drawable.person_2),
        {}, ChatUiState(), {}
    )
}
