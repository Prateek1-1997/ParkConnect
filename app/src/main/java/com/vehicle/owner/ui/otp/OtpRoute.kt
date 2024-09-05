package com.vehicle.owner.ui.otp

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vehicle.owner.auth.FirebasePhoneAuthClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpRoute(
    userId: String,
    phoneNumber: String,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToRegistrationScreen: () -> Unit,
    onBack: () -> Unit,
    mainActivity: Activity,
) {
    val viewModel = hiltViewModel<OtpViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var timeLeft by remember { mutableStateOf(60) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val uiEvent: (OtpUiIntent) -> Unit = { intent ->
        coroutineScope.launch {
            viewModel.intents.send(intent)
        }
    }
    val firebasePhoneAuthClient by lazy {
        FirebasePhoneAuthClient()
    }
    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    LaunchedEffect("side_effect") {
        var fcmToken = ""
        firebasePhoneAuthClient.getFcmToken {
            fcmToken = it
        }
        uiEvent.invoke(OtpUiIntent.Init(userId, phoneNumber))
        firebasePhoneAuthClient.startPhoneNumberVerification(
            activity = mainActivity,
            phoneNumber = "+91${phoneNumber}"
        )
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is OtpUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        effect.message,
                    )
                }

                is OtpUiEffect.NavigateToHomeScreen -> onNavigateToHomeScreen()

                is OtpUiEffect.NavigateToRegistrationScreen -> onNavigateToRegistrationScreen()

                is OtpUiEffect.VerifyOtp -> firebasePhoneAuthClient.verifyOtp(
                    effect.otp,
                    onOtpSuccessful = {
                        uiEvent.invoke(
                            OtpUiIntent.SendFcm(
                                fcmToken,
                                firebaseUuid = it?.uid.orEmpty()
                            )
                        )
                    })
            }
        }.collect()
    }

    OtpScreen(
        uiEvent = { intent ->
            coroutineScope.launch {
                viewModel.intents.send(intent)
            }
        },
        uiState = uiState,
        countDownTimer = timeLeft,
        scrollBehavior = scrollBehavior,
        onBack = onBack,
    )
}