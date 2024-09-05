package com.vehicle.owner.ui.otp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vehicle.owner.ui.otp.OtpUiIntent
import com.vehicle.owner.ui.otp.OtpUiState
import com.vehicle.owner.ui.theme.primaryColor

@Composable
fun OtpScreenContent(
    modifier: Modifier = Modifier, countdownTimer: Int,
    uiEvent: (OtpUiIntent) -> Unit,
    uiState: OtpUiState,
) {
    var otpValue by remember {
        mutableStateOf("")
    }
    var isOtpInputFilled by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Input OTP Code",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Code is sent to ${uiState.phoneNumber}. Please check yout SMS.",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(24.dp))
        OtpTextField(
            otpText = otpValue,
            onOtpTextChange = { value, otpInputFilled ->
                otpValue = value
                isOtpInputFilled = otpInputFilled
                if(isOtpInputFilled) {
                    uiEvent.invoke(OtpUiIntent.VerifyOtp(otpValue))
                }
            },
            isWrong = uiState.isOtpWrong,
            otpCount = 6,
            modifier = Modifier.fillMaxWidth(),
            isOtpFilled = isOtpInputFilled,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text(
                text = "Resend OTP ?",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            if (countdownTimer == 0) {
                Text(
                    text = "Resend",
                    style = MaterialTheme.typography.titleMedium,
                    color = primaryColor,
                    modifier = Modifier.clickable {
                        uiEvent.invoke(OtpUiIntent.ResendOtp)
                    }
                )
            } else {
                Text(
                    text = "00:${String.format("%02d", countdownTimer)}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun OtpScreenContentPreview() {
    OtpScreenContent(countdownTimer = 60, uiEvent = {}, uiState = OtpUiState())
}