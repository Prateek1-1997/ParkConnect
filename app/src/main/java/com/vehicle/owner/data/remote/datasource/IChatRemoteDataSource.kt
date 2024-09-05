package com.vehicle.owner.data.remote.datasource

import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.network.NetworkResultWrapper

interface IChatRemoteDataSource {

    suspend fun getChatHistory(userId: String): NetworkResultWrapper<List<ChatModel>>

    suspend fun sendChatHistory(
        userId: String,
        message: String
    ): NetworkResultWrapper<List<ChatModel>>


}