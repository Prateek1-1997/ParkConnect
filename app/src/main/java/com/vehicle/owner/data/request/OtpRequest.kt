package com.vehicle.owner.data.request

import com.google.gson.annotations.SerializedName

data class OtpRequest(
    @SerializedName("email") val phone: String,
)

data class VerifyOtpRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("firebase_uuid") val firebaseUuid: String,
    @SerializedName("fcm") val fcmToken: String,
)

data class UpdateUserDetailsRequest(
    @SerializedName("name") val name: String,
    @SerializedName("vehicle_number") val vehicleNumber: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("tnc") val tnc: Boolean = true,
)

data class ChatRequest(
    @SerializedName("message") val message: String,
)

