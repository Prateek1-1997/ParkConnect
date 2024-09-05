package com.vehicle.owner.data.remote.service

import com.vehicle.owner.data.request.ChatRequest
import com.vehicle.owner.data.response.BaseDto
import com.vehicle.owner.data.response.ChatDto
import com.vehicle.owner.data.response.DtoItems
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {

    @GET("/api/chat/{user_id}")
    suspend fun getChatHistory(
        @Path("user_id") userId: String,
    ): Response<BaseDto<DtoItems<ChatDto>>>

    @POST("/api/chat/{user_id}")
    suspend fun sendChatMessage(
        @Path("user_id") userId: String,
        @Body body: ChatRequest,
    ): Response<BaseDto<DtoItems<ChatDto>>>


}