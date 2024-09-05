package com.vehicle.owner.ui.registration

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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationRoute(
    onNavigateToHomeScreen: () -> Unit,
    onBack: () -> Unit,
) {
    val viewModel = hiltViewModel<RegistrationViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    LaunchedEffect("side_effect") {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is RegistrationUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        effect.message,
                    )
                }

                is RegistrationUiEffect.NavigateToHomeScreen -> onNavigateToHomeScreen()
                

            }
        }.collect()
    }

    RegistrationScreen(
        uiEvent = { intent ->
            coroutineScope.launch {
                viewModel.intents.send(intent)
            }
        },
        uiState = uiState,
        onBack = onBack,
        scrollBehavior = scrollBehavior,
    )
}