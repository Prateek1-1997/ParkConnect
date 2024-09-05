package com.vehicle.owner.network

sealed class NetworkResultWrapper<out T> {
    data class Success<out T>(val data: T) : NetworkResultWrapper<T>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
        val throwable: Throwable? = null,
    ) : NetworkResultWrapper<Nothing>()
}
