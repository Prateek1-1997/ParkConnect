package com.vehicle.owner.ui.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessaging
import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.data.local.ISharedPreference
import com.vehicle.owner.domain.usecase.GenerateOtpUsecase
import com.vehicle.owner.domain.usecase.VerifyOtpUsecase
import com.vehicle.owner.ui.otp.OtpUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val generateOtpUsecase: GenerateOtpUsecase,
    private val verifyOtpUsecase: VerifyOtpUsecase,
    private val sharedPreference: ISharedPreference,
    ) : ViewModel() {

    val intents: Channel<AuthenticationUiIntent> = Channel(Channel.UNLIMITED)

    private val _uiState =
        MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState.asStateFlow()

    private val _uiEffect: Channel<AuthenticationUiEffect> = Channel()
    val uiEffect: Flow<AuthenticationUiEffect> = _uiEffect.receiveAsFlow()

    private var phoneNumber = ""
    private var fcmToken = ""

    init {
        handleIntent()
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fcmToken = task.result
                    Log.d("FCM Token", "Token: $fcmToken")
                } else {
                    Log.e("FCM Token", "Failed to get token: ${task.exception}")
                }
            }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is AuthenticationUiIntent.PhoneNumberInput -> phoneNumber = intent.number
                    is AuthenticationUiIntent.AuthenticatedCta -> handleContinueCta(intent.firebaseUser)
                }
            }
        }
    }

    private suspend fun handleContinueCta(firebaseUser: FirebaseUser?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (
                val result = generateOtpUsecase(
                    firebaseUser?.email.orEmpty()
                )
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "handleContinueCta: error ${result.error}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }

                is CustomResult.Success -> {
                    Log.e("TAG", "handleContinueCta: success User Id ${result.data}")
                    /*_uiEffect.send(
                        AuthenticationUiEffect.NavigateToOtpScreen(
                            result.data,
                            phoneNumber
                        )
                    )*/
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                    handleVerifyOtp(fcmToken, firebaseUser?.uid.orEmpty(), result.data)
                }
            }
        }
    }

    private suspend fun handleVerifyOtp(fcmToken: String, firebaseUuid: String, userId: String) {
        Log.e("TAG", "handleVerifyOtp: ${fcmToken} ${firebaseUuid}")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (
                val result = verifyOtpUsecase.invoke(
                    userId, fcmToken, firebaseUuid
                )
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "handleVerifyOtp: error ${result.error}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }

                is CustomResult.Success -> {
                    Log.e("TAG", "handleVerifyOtp: ${result.data}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                    sharedPreference.saveAuthToken(result.data.token)
                    sharedPreference.saveUserId(userId)
                    _uiEffect.send(AuthenticationUiEffect.NavigateToRegistrationScreen)
                }
            }
        }
    }
}