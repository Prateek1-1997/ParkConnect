package com.vehicle.owner.ui.authentication

import com.google.firebase.auth.FirebaseUser

data class AuthenticationUiState(
    val isIdle: Boolean = true,
    val isLoading: Boolean = false
)

sealed class AuthenticationUiEffect {
    data class ShowError(val message: String) : AuthenticationUiEffect()
    data class NavigateToOtpScreen(val userId: String,val phoneNumber: String) : AuthenticationUiEffect()
    object NavigateToRegistrationScreen : AuthenticationUiEffect()
}

sealed class AuthenticationUiIntent {
    data class AuthenticatedCta(val firebaseUser: FirebaseUser?) : AuthenticationUiIntent()
    data class PhoneNumberInput(val number: String) : AuthenticationUiIntent()

}