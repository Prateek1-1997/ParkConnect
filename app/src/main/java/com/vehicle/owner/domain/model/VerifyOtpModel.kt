package com.vehicle.owner.domain.model


data class VerifyOtpModel(
    val message: String,
    val isVerified: Boolean,
    val token: String,
)
