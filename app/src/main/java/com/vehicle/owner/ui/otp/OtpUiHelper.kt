package com.vehicle.owner.ui.otp

data class OtpUiState(
    val isIdle: Boolean = true,
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val isOtpWrong: Boolean = false,
)

sealed class OtpUiEffect {
    data class ShowError(val message: String) : OtpUiEffect()
    object NavigateToHomeScreen : OtpUiEffect()
    object NavigateToRegistrationScreen : OtpUiEffect()
    data class VerifyOtp(val otp:String) : OtpUiEffect()
}

sealed class OtpUiIntent {
    data class VerifyOtp(val otp: String) : OtpUiIntent()
    object ResendOtp : OtpUiIntent()

    data class Init(val userId: String, val phoneNumber: String) : OtpUiIntent()
    data class SendFcm(val fcmToken: String, val firebaseUuid: String) : OtpUiIntent()
}