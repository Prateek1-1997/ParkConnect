package com.vehicle.owner.ui.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.vehicle.owner.auth.FirebaseGoogleAuthClient
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun AuthenticationRoute(
    onNavigateToOtpScreen: (String, String) -> Unit,
    onNavigateToRegistrationScreen : () -> Unit,
    mainActivity: Activity,
) {
    val viewModel = hiltViewModel<AuthenticationViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val firebaseGoogleAuthClient by lazy {
        FirebaseGoogleAuthClient(mainActivity)
    }
    val activityResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("TAG", "AuthenticationRoute: ${result.resultCode}")
            Log.e("TAG", "AuthenticationRoute: ${Activity.RESULT_OK}")
            Log.e("TAG", "AuthenticationRoute: ${result.data}")
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result here
                val data: Intent? = result.data
                // Process the data
                firebaseGoogleAuthClient.handleOnActivityResult(data) {
                    coroutineScope.launch {
                        viewModel.intents.send(AuthenticationUiIntent.AuthenticatedCta(it))
                    }
                }
            } else {
                Firebase.crashlytics.recordException(Exception("activityResultLauncher: Failed ${result.resultCode} ${result.data} ${result}"))
            }
        }

    LaunchedEffect("side_effect") {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is AuthenticationUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        effect.message,
                    )
                }

                is AuthenticationUiEffect.NavigateToOtpScreen -> onNavigateToOtpScreen(
                    effect.userId,
                    effect.phoneNumber
                )

                is AuthenticationUiEffect.NavigateToRegistrationScreen -> onNavigateToRegistrationScreen()
            }
        }.collect()
    }

    AuthenticationScreen(
        uiEvent = { intent ->
            coroutineScope.launch {
                viewModel.intents.send(intent)
            }
        },
        uiState = uiState,
        onContinueCta = {
            activityResultLauncher.launch(firebaseGoogleAuthClient.signIn())
        }
    )
}