package com.vehicle.owner.ui.registration

import okhttp3.MultipartBody

data class RegistrationUiState(
    val isIdle: Boolean = true,
    val isLoading: Boolean = false
)

sealed class RegistrationUiEffect {
    data class ShowError(val message: String) : RegistrationUiEffect()
    object NavigateToHomeScreen : RegistrationUiEffect()

}

sealed class RegistrationUiIntent {
    data class ContinueCta(
        val ownerName: String,
        val vehicleNumber: String,
        val rcImage: MultipartBody.Part,
        val fileName: String?,
        val filePath: String?,
        val phoneNumber: String
    ) : RegistrationUiIntent()
}