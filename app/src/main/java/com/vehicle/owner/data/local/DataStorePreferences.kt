package com.vehicle.owner.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject

class DataStorePreferences @Inject constructor(dataStore: DataStore<Preferences>) :
    BaseDataStorePreference(dataStore), IDataStorePreference {

    override suspend fun getAuthToken(): String? {
        return getValue(Keys.authToken)
    }

    override suspend fun saveAuthToken(authToken: String) {
        setValue(Keys.authToken, authToken)
    }

    override suspend fun getUserId(): String {
        return getValue(Keys.userId) ?: ""
    }

    override suspend fun saveUserId(userId: String) {
        setValue(Keys.userId, userId)
    }

}

internal object Keys {
    val authToken = stringPreferencesKey("auth_token")
    val userId = stringPreferencesKey("user_id")
}