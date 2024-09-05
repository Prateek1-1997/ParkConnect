package com.vehicle.owner.data.local

interface IDataStorePreference {
    suspend fun getAuthToken() : String?
    suspend fun saveAuthToken(authToken : String)

    suspend fun getUserId() : String
    suspend fun saveUserId(userId: String)
}