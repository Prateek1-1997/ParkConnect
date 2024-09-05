package com.vehicle.owner.network

import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    apiCallBlock: suspend () -> Response<T>,
): Result<T> {
    return try {
        val response = apiCallBlock.invoke()
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception(response.errorBody()?.string().toString()))
        }
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
}

suspend inline fun <T> executeSafeCall(
    block: () -> T,
    error: (String) -> T,
): T {
    return try {
        block.invoke()
    } catch (e: Exception) {
        val textRes = when (e) {
            is SocketTimeoutException -> "Unknown Error"

            is UnknownHostException -> "No Internet"

            is IOException -> "Unknown Error"
            else -> ""
        }
        error.invoke(textRes)
    }
}
