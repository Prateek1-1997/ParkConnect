package com.vehicle.owner.data.local

interface ISharedPreference {
    fun getAuthToken() : String
    fun saveAuthToken(authToken : String)

    fun getUserId() : String
    fun saveUserId(userId: String)

    fun isVerified() : Boolean

    fun saveVerified(isVerified : Boolean)

    companion object {
        const val SHARED_PREF = "shared_pref"
        const val USER_ID = "user_id"
        const val TOKEN = "token"
        const val IS_VERIFIED = "is_verified"
    }
}