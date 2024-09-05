package com.vehicle.owner.data.remote.datasource

import com.vehicle.owner.data.remote.mapper.Mapper.toDomainModel
import com.vehicle.owner.data.remote.service.ChatService
import com.vehicle.owner.data.request.ChatRequest
import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.network.NetworkResultWrapper
import com.vehicle.owner.network.safeApiCall
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val chatService: ChatService,
) : IChatRemoteDataSource {


    override suspend fun getChatHistory(userId: String): NetworkResultWrapper<List<ChatModel>> {
        safeApiCall { chatService.getChatHistory(userId) }.onSuccess {
            return NetworkResultWrapper.Success(it.data?.toDomainModel().orEmpty())
        }.onFailure {
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

    override suspend fun sendChatHistory(
        userId: String,
        message: String
    ): NetworkResultWrapper<List<ChatModel>> {
        safeApiCall { chatService.sendChatMessage(userId, ChatRequest(message)) }.onSuccess {
            return NetworkResultWrapper.Success(it.data?.toDomainModel().orEmpty())
        }.onFailure {
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

}