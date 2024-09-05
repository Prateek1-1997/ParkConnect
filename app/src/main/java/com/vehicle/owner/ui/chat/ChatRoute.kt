package com.vehicle.owner.ui.chat

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vehicle.owner.ui.chat.data.Person
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoute(
    person: Person,
    onBack: () -> Unit,
) {
    val viewModel = hiltViewModel<ChatViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val uiEvent: (ChatUiIntent) -> Unit = { intent ->
        coroutineScope.launch {
            viewModel.intents.send(intent)
        }
    }
    LaunchedEffect(Unit) {
        uiEvent.invoke(ChatUiIntent.Init(person))
    }
    LaunchedEffect("side_effect") {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is ChatUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        effect.message,
                    )
                }
            }
        }.collect()
    }

    ChatScreen(data = person, uiEvent, uiState, onBack)

}