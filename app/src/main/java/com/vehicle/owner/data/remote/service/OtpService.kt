package com.vehicle.owner.data.remote.service

import com.vehicle.owner.data.request.OtpRequest
import com.vehicle.owner.data.request.UpdateUserDetailsRequest
import com.vehicle.owner.data.request.VerifyOtpRequest
import com.vehicle.owner.data.response.BaseDto
import com.vehicle.owner.data.response.OtpDto
import com.vehicle.owner.data.response.UserDto
import com.vehicle.owner.data.response.VerifyOtpDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface OtpService {

    @POST("/api/send-otp")
    suspend fun generateOtp(
        @Body body : OtpRequest,
    ): Response<BaseDto<OtpDto>>


    @POST("/api/verify-otp")
    suspend fun verifyOtp(
        @Body body : VerifyOtpRequest,
    ): Response<BaseDto<VerifyOtpDto>>

    @GET("/api/user")
    suspend fun getUserDetails(
    ): Response<BaseDto<UserDto>>

    @PUT("/api/user/{user_id}")
    suspend fun updateUserDetails(
        @Path("user_id") userId : String,
        @Body body : UpdateUserDetailsRequest
    ): Response<BaseDto<Any>>

    @Multipart
    @POST("/api/upload-rc")
    fun uploadImage(
        @Part("vehicle_number") vehicleNumber: String,
        @Part image: MultipartBody.Part
    ): Response<Any>


    //@Multipart
    @POST("/api/upload-rc")
    suspend fun uploadImageTest(
        @Body body: MultipartBody,
    ): Response<Any>

}