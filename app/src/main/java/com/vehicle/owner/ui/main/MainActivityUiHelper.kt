package com.vehicle.owner.ui.main

data class MainActivityUiState(
    val isLoading: Boolean = true,
    val isAuthenticated: Boolean? = null,
    val isVerified : Boolean = false,
)