package com.vehicle.owner.domain.model

import com.vehicle.owner.data.response.VehicleDto


data class UserModel(
    val phone: String,
    val isVerified: Boolean,
    val createdDate: String,
    val updatedDate: String,
    val name: String,
    val userId: String,
    val vehicleData: ArrayList<VehicleDto> = arrayListOf()
)