package com.vehicle.owner.core

sealed interface CustomResult<out S, out E> {
    data class Success<S>(val data: S) : CustomResult<S, Nothing>
    data class Error<E>(val error: E) : CustomResult<Nothing, E>
}
