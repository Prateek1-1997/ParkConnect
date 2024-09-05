package com.vehicle.owner.ui.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.vehicle.owner.core.components.TraditionalToolbar
import com.vehicle.owner.ui.otp.component.OtpScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    uiEvent: (OtpUiIntent) -> Unit,
    uiState: OtpUiState,
    countDownTimer: Int,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TraditionalToolbar(scrollBehavior, onBack, "Verification OTP")
        },
        snackbarHost = {
            SnackbarHost(hostState = SnackbarHostState())
        },
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .background(Color.White)
        ) {
            OtpScreenContent(countdownTimer = countDownTimer, uiEvent = uiEvent, uiState = uiState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun OtpScreenPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        OtpScreen(
            uiEvent = {},
            uiState = OtpUiState(),
            countDownTimer = 60,
            onBack = {},
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        )
    }
}