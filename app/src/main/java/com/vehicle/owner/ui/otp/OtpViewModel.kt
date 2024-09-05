package com.vehicle.owner.ui.otp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.data.local.ISharedPreference
import com.vehicle.owner.domain.usecase.VerifyOtpUsecase
import com.vehicle.owner.ui.otp.OtpUiEffect
import com.vehicle.owner.ui.otp.OtpUiIntent
import com.vehicle.owner.ui.otp.OtpUiState
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
class OtpViewModel @Inject constructor(
    private val verifyOtpUsecase: VerifyOtpUsecase,
    private val sharedPreference: ISharedPreference,
) : ViewModel() {

    val intents: Channel<OtpUiIntent> = Channel(Channel.UNLIMITED)

    private val _uiState =
        MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    private val _uiEffect: Channel<OtpUiEffect> = Channel()
    val uiEffect: Flow<OtpUiEffect> = _uiEffect.receiveAsFlow()

    private var userId: String = ""

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is OtpUiIntent.Init -> {
                        userId = intent.userId
                        _uiState.value = _uiState.value.copy(
                            phoneNumber = intent.phoneNumber
                        )
                    }

                    is OtpUiIntent.VerifyOtp -> {
                        if (intent.otp.length == 6)
                            _uiEffect.send(OtpUiEffect.VerifyOtp(intent.otp))
                    }

                    is OtpUiIntent.SendFcm -> handleVerifyOtp(
                        fcmToken = intent.fcmToken,
                        firebaseUuid = intent.firebaseUuid
                    )

                    else -> {}
                }
            }
        }
    }

    private suspend fun handleVerifyOtp(fcmToken: String, firebaseUuid: String) {
        Log.e("TAG", "handleVerifyOtp: ${fcmToken} ${firebaseUuid}")
        viewModelScope.launch {
            when (
                val result = verifyOtpUsecase.invoke(
                    userId, fcmToken, firebaseUuid
                )
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "handleVerifyOtp: error ${result.error}")
                }

                is CustomResult.Success -> {
                    Log.e("TAG", "handleVerifyOtp: ${result.data}")
                    sharedPreference.saveAuthToken(result.data.token)
                    sharedPreference.saveUserId(userId)
                    _uiEffect.send(OtpUiEffect.NavigateToRegistrationScreen)
                }
            }
        }
    }
}