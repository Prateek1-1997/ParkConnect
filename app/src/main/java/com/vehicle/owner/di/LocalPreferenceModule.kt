package com.vehicle.owner.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.vehicle.owner.data.local.BaseDataStorePreference
import com.vehicle.owner.data.local.DataStorePreferences
import com.vehicle.owner.data.local.IDataStorePreference
import com.vehicle.owner.data.local.ISharedPreference
import com.vehicle.owner.data.local.ISharedPreference.Companion.SHARED_PREF
import com.vehicle.owner.data.local.getDataStoreObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalPreferenceModule {

    @Provides
    @Singleton
    fun dataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        getDataStoreObject(appContext)

    @Provides
    @Singleton
    fun providesDefaultDataStore(
        dataStore: DataStore<Preferences>,
    ): BaseDataStorePreference {
        return DataStorePreferences(dataStore)
    }

    @Provides
    @Singleton
    fun providesDefaultIDataStore(
        dataStore: DataStore<Preferences>,
    ): IDataStorePreference {
        return DataStorePreferences(dataStore)
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context,
        gson: Gson,
    ): ISharedPreference {
        return com.vehicle.owner.data.local.SharedPreferences(sharedPreferences, context, gson)
    }

    @Provides
    @Singleton
    fun providesISharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE,
        )
    }

}