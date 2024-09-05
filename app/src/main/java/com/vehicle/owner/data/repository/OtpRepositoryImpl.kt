package com.vehicle.owner.data.repository

import com.vehicle.owner.data.remote.datasource.IOtpRemoteDataSource
import com.vehicle.owner.domain.model.UserModel
import com.vehicle.owner.domain.model.VerifyOtpModel
import com.vehicle.owner.domain.respository.IOtpRepository
import com.vehicle.owner.network.NetworkResultWrapper
import okhttp3.MultipartBody
import javax.inject.Inject

class OtpRepositoryImpl @Inject constructor(
    private val otpRemoteDataSource: IOtpRemoteDataSource,
) : IOtpRepository {

    override suspend fun sendOtp(phoneNumber: String): NetworkResultWrapper<String> {
        return otpRemoteDataSource.sendOtp(phoneNumber)
    }

    override suspend fun verifyOtp(
        userId: String,
        fcmToken: String,
        firebaseUuid: String,
    ): NetworkResultWrapper<VerifyOtpModel> {
        return otpRemoteDataSource.verifyOtp(userId, fcmToken, firebaseUuid)
    }

    override suspend fun getUserDetails(): NetworkResultWrapper<UserModel> {
        return otpRemoteDataSource.getUserDetails()
    }

    override suspend fun updateUserDetails(
        userId: String,
        name: String,
        vehicleNumber: String,
        phoneNumber: String
    ): NetworkResultWrapper<Any> {
        return otpRemoteDataSource.updateUserDetails(userId, name, vehicleNumber,phoneNumber)
    }

    override suspend fun uploadRc(uploadBody: MultipartBody): NetworkResultWrapper<Any> {
        return otpRemoteDataSource.uploadRc(uploadBody)
    }

    override suspend fun uploadRc(
        vehicleNumber: String,
        rcImage: MultipartBody.Part
    ): NetworkResultWrapper<Any> {
        return otpRemoteDataSource.uploadRc(vehicleNumber, rcImage)
    }

}