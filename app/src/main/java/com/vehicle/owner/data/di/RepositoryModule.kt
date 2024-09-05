package com.vehicle.owner.data.di

import com.vehicle.owner.data.repository.ChatRepositoryImpl
import com.vehicle.owner.data.repository.OtpRepositoryImpl
import com.vehicle.owner.data.repository.SearchRepositoryImpl
import com.vehicle.owner.domain.respository.IChatRepository
import com.vehicle.owner.domain.respository.IOtpRepository
import com.vehicle.owner.domain.respository.ISearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOtpSourceProviders(
        otpRepositoryImpl: OtpRepositoryImpl,
    ): IOtpRepository

    @Binds
    abstract fun bindSearchSourceProviders(
        searchRepositoryImpl: SearchRepositoryImpl,
    ): ISearchRepository

    @Binds
    abstract fun bindChatSourceProviders(
        chatRepositoryImpl: ChatRepositoryImpl
    ): IChatRepository
}
