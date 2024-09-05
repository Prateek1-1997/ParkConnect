package com.vehicle.owner.data.remote.datasource

import android.util.Log
import com.vehicle.owner.data.remote.mapper.Mapper.toDomainModel
import com.vehicle.owner.data.remote.service.OtpService
import com.vehicle.owner.data.request.OtpRequest
import com.vehicle.owner.data.request.UpdateUserDetailsRequest
import com.vehicle.owner.data.request.VerifyOtpRequest
import com.vehicle.owner.domain.model.UserModel
import com.vehicle.owner.domain.model.VerifyOtpModel
import com.vehicle.owner.network.NetworkResultWrapper
import com.vehicle.owner.network.safeApiCall
import okhttp3.MultipartBody
import javax.inject.Inject

class OtpRemoteDataSourceImpl @Inject constructor(
    private val otpService: OtpService,
) : IOtpRemoteDataSource {

    override suspend fun sendOtp(phoneNumber: String): NetworkResultWrapper<String> {
        val generateOTPBody = OtpRequest(phoneNumber)
        safeApiCall { otpService.generateOtp(generateOTPBody) }.onSuccess {
            Log.e("TAG", "sendOtp: Success ${it}")
            return if (it.data?.userId != "") {
                NetworkResultWrapper.Success(it.data?.userId ?: " ")
            } else {
                NetworkResultWrapper.Error(
                    throwable = Throwable("User Id Not Found"),
                )
            }
        }.onFailure {
            Log.e("TAG", "sendOtp: Failure ${it}")
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

    override suspend fun verifyOtp(
        userId: String,
        fcmToken: String,
        firebaseUuid: String,
    ): NetworkResultWrapper<VerifyOtpModel> {
        val verifyOtpRequest = VerifyOtpRequest(userId, firebaseUuid, fcmToken)
        safeApiCall { otpService.verifyOtp(verifyOtpRequest) }.onSuccess {
            Log.e("TAG", "verifyOtp: Success ${it}")
            it.data?.toDomainModel()?.let {
                return NetworkResultWrapper.Success(it)
            } ?: run {
                return NetworkResultWrapper.Error(
                    throwable = Throwable("Something Went Wrong"),
                )
            }
        }.onFailure {
            Log.e("TAG", "verifyOtp: Failure ${it}")
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

    override suspend fun getUserDetails(): NetworkResultWrapper<UserModel> {
        safeApiCall { otpService.getUserDetails() }.onSuccess {
            Log.e("TAG", "getUserDetails: Success ${it}")
            return NetworkResultWrapper.Success(
                it.data.toDomainModel()
            )
        }.onFailure {
            Log.e("TAG", "getUserDetails: Failure ${it}")
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

    override suspend fun updateUserDetails(
        userId: String,
        name: String,
        vehicleNumber: String,
        phoneNumber: String
    ): NetworkResultWrapper<Any> {
        safeApiCall {
            otpService.updateUserDetails(
                userId,
                UpdateUserDetailsRequest(name, vehicleNumber,phoneNumber)
            )
        }.onSuccess {
            Log.e("TAG", "updateUserDetails: Success ${it}")
            return NetworkResultWrapper.Success(
                it
            )
        }.onFailure {
            Log.e("TAG", "updateUserDetails: Failure ${it}")
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

    override suspend fun uploadRc(uploadBody: MultipartBody): NetworkResultWrapper<Any> {
        safeApiCall {
            otpService.uploadImageTest(
                uploadBody
            )
        }.onSuccess {
            Log.e("TAG", "uploadRc: ${it}")
            return NetworkResultWrapper.Success(
                it
            )
        }.onFailure {
            Log.e("TAG", "uploadRc: ${it}")
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

    override suspend fun uploadRc(
        vehicleNumber: String,
        rcImage: MultipartBody.Part
    ): NetworkResultWrapper<Any> {
        safeApiCall {
            otpService.uploadImage(
                vehicleNumber,
                rcImage
            )
        }.onSuccess {
            Log.e("TAG", "uploadRc: Success ${it}")
            return NetworkResultWrapper.Success(
                it
            )
        }.onFailure {
            Log.e("TAG", "uploadRc: Failure ${it}")
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

}