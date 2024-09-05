package com.vehicle.owner.domain.respository

import com.vehicle.owner.domain.model.UserModel
import com.vehicle.owner.domain.model.VerifyOtpModel
import com.vehicle.owner.network.NetworkResultWrapper
import okhttp3.MultipartBody

interface IOtpRepository {

    suspend fun sendOtp(phoneNumber: String): NetworkResultWrapper<String>

    suspend fun verifyOtp(
        userId: String,
        fcmToken: String,
        firebaseUuid: String,
    ): NetworkResultWrapper<VerifyOtpModel>

    suspend fun getUserDetails(): NetworkResultWrapper<UserModel>

    suspend fun updateUserDetails(
        userId: String,
        name: String,
        vehicleNumber: String,
        phoneNumber: String
    ): NetworkResultWrapper<Any>

    suspend fun uploadRc(uploadBody: MultipartBody): NetworkResultWrapper<Any>


    suspend fun uploadRc(
        vehicleNumber: String,
        rcImage: MultipartBody.Part
    ): NetworkResultWrapper<Any>

}