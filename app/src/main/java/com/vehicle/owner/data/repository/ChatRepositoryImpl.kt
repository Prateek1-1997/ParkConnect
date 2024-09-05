package com.vehicle.owner.data.repository

import com.vehicle.owner.data.remote.datasource.IChatRemoteDataSource
import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.domain.respository.IChatRepository
import com.vehicle.owner.network.NetworkResultWrapper
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDataSource: IChatRemoteDataSource,
) : IChatRepository {

    override suspend fun getChatHistory(userId: String): NetworkResultWrapper<List<ChatModel>> {
        return chatRemoteDataSource.getChatHistory(userId)
    }

    override suspend fun sendChatHistory(
        userId: String,
        message: String
    ): NetworkResultWrapper<List<ChatModel>> {
        return chatRemoteDataSource.sendChatHistory(userId, message)
    }


}