package com.vehicle.owner.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "whoIsCar",
)

fun getDataStoreObject(context: Context): DataStore<Preferences> {
    return context.dataStore
}