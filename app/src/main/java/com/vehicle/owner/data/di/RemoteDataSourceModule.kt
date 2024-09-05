package com.vehicle.owner.data.di

import com.vehicle.owner.data.remote.datasource.ChatRemoteDataSourceImpl
import com.vehicle.owner.data.remote.datasource.IChatRemoteDataSource
import com.vehicle.owner.data.remote.datasource.IOtpRemoteDataSource
import com.vehicle.owner.data.remote.datasource.ISearchRemoteDataSource
import com.vehicle.owner.data.remote.datasource.OtpRemoteDataSourceImpl
import com.vehicle.owner.data.remote.datasource.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindIOtpRemoteDataSourceProviders(
        otpRemoteDataSourceImpl: OtpRemoteDataSourceImpl,
    ): IOtpRemoteDataSource

    @Binds
    abstract fun bindISearchRemoteDataSourceProviders(
        searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl,
    ): ISearchRemoteDataSource

    @Binds
    abstract fun bindIChatRemoteDataSourceProviders(
        chatRemoteDataSourceImpl: ChatRemoteDataSourceImpl,
    ): IChatRemoteDataSource
}
