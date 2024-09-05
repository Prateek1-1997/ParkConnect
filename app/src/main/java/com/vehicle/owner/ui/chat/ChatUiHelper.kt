package com.vehicle.owner.ui.chat

import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.ui.chat.data.Person

data class ChatUiState(
    val chatHistory: List<ChatModel> = emptyList(),
    val person: Person? = null,
)

sealed class ChatUiEffect {
    data class ShowError(val message: String) : ChatUiEffect()
}

sealed class ChatUiIntent {
    data class Init(val person: Person) : ChatUiIntent()
    data class SendMessage(val message: String) : ChatUiIntent()
}