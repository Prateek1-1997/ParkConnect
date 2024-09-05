package com.vehicle.owner.data.response

import com.google.errorprone.annotations.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseDto<T>(
    @SerializedName("data") val data: T?,
)
@Keep
data class DtoItems<T>(
    @SerializedName("items") val items: List<T>?,
)

@Keep
data class OtpDto(
    @SerializedName("message") val message: String?,
    @SerializedName("user_id") val userId: String?,
)

@Keep
data class VerifyOtpDto(
    @SerializedName("message") val message: String?,
    @SerializedName("is_verified") val isVerified: Boolean?,
    @SerializedName("token") val token: String?,
)

@Keep
data class VehicleDto(
    @SerializedName("vehicle_number") var vehicleNumber: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("updated_date") var updatedDate: String? = null,
    @SerializedName("user_id") var userId: String? = null
)

@Keep
data class UserDto(

    @SerializedName("phone") var phone: String?,
    @SerializedName("is_verified") var isVerified: Boolean?,
    @SerializedName("created_date") var createdDate: String?,
    @SerializedName("updated_date") var updatedDate: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("user_id") var userId: String?,
    @SerializedName("vehicle_data") var vehicleData: ArrayList<VehicleDto> = arrayListOf()

)

@Keep
data class ChatDto(
    @SerializedName("id") val id: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("sender") val sender: String?,
    @SerializedName("reciever") val receiver: String?,
    @SerializedName("created_date") val timeStamp: String?,
    @SerializedName("direction") val direction: Boolean?,
)