package com.vehicle.owner.data.response

data class VehicleData(
    val vehicle_info: List<VehicleInfo>?
)

data class VehicleInfo(
    val vehicle_number: String,
    val user_id: String
)




