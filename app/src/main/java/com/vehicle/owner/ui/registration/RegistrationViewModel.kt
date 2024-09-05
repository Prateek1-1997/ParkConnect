package com.vehicle.owner.ui.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.data.local.ISharedPreference
import com.vehicle.owner.data.remote.service.OtpService
import com.vehicle.owner.domain.usecase.GetUserDetailsUsecase
import com.vehicle.owner.domain.usecase.UpdateUserDetailsUsecase
import com.vehicle.owner.domain.usecase.UploadRcUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val getUserDetailsUsecase: GetUserDetailsUsecase,
    private val updateUserDetailsUsecase: UpdateUserDetailsUsecase,
    private val uploadRcUsecase: UploadRcUsecase,
    private val sharedPreference: ISharedPreference,
    private val otpService: OtpService,
) : ViewModel() {

    val intents: Channel<RegistrationUiIntent> = Channel(Channel.UNLIMITED)

    private val _uiState =
        MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _uiEffect: Channel<RegistrationUiEffect> = Channel()
    val uiEffect: Flow<RegistrationUiEffect> = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
        getUserDetails()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is RegistrationUiIntent.ContinueCta -> handleContinueCta(
                        intent.ownerName,
                        intent.vehicleNumber,
                        intent.rcImage,
                        intent.fileName,
                        intent.filePath,
                        intent.phoneNumber
                    )
                }
            }
        }
    }

    private suspend fun handleContinueCta(
        ownerName: String,
        vehicleNumber: String,
        rcImage: MultipartBody.Part,
        fileName: String?,
        filePath: String?,
        phoneNumber: String
    ) {
        Log.e("TAG", "handleContinueCta: ${ownerName} ${vehicleNumber}")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (
                val result = updateUserDetailsUsecase.invoke(
                    sharedPreference.getUserId(),
                    ownerName,
                    vehicleNumber,
                    phoneNumber
                )
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "handleContinueCta: error ${result.error}")
                }

                is CustomResult.Success -> {
                    Log.e("TAG", "handleContinueCta: ${result.data}")
                    uploadRc(vehicleNumber, rcImage, fileName, filePath)
                }
            }
        }
        //_uiEffect.send(RegistrationUiEffect.NavigateToOtpScreen)
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            when (
                val result = getUserDetailsUsecase()
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "getUserDetails: error ${result.error}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }

                is CustomResult.Success -> {
                    Log.e("TAG", "getUserDetails: ${result.data}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                    if (result.data.isVerified) {
                        sharedPreference.saveVerified(true)
                        _uiEffect.send(RegistrationUiEffect.NavigateToHomeScreen)
                    }
                }
            }
        }
    }

    private fun uploadRc(
        vehicleNumber: String,
        rcImage: MultipartBody.Part,
        fileName: String?,
        filePath: String?
    ) {
        val file = File(filePath.orEmpty())
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("vehicle_number", vehicleNumber)
            .addFormDataPart("file_obj", fileName, requestBody)
            .build()
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            when (
                val result = uploadRcUsecase(multipartBody)
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "uploadRc: error ${result.error}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }

                is CustomResult.Success -> {
                    Log.e("TAG", "uploadRc: ${result.data}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                    sharedPreference.saveVerified(true)
                    _uiEffect.send(RegistrationUiEffect.NavigateToHomeScreen)
                }
            }
        }
    }

    /*private fun test(
        vehicleNumber: String,
        rcImage: MultipartBody.Part,
        fileName: String?,
        filePath: String?
    ) {
        val file = File(filePath.orEmpty())
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("vehicle_number", vehicleNumber)
            .addFormDataPart("file_obj", fileName, requestBody)
            .build()
        val call = otpService.uploadImageTest(multipartBody)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    Log.e("TAG", "onResponse: Success ${response}")
                    sharedPreference.saveVerified(true)
                } else {
                    // Handle unsuccessful response
                    Log.e("TAG", "onResponse: Failure ${response}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
                Log.e("TAG", "onResponse: onFailure ${t}")
            }
        })
    }*/
}