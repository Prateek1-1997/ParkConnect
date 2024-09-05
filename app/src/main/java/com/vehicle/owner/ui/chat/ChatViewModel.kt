package com.vehicle.owner.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.domain.usecase.GetChatHistoryUsecase
import com.vehicle.owner.domain.usecase.SendChatMessageUsecase
import com.vehicle.owner.ui.chat.data.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatHistoryUsecase: GetChatHistoryUsecase,
    private val sendChatMessageUsecase: SendChatMessageUsecase,
) : ViewModel() {

    val intents: Channel<ChatUiIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState>
        get() = _state

    private val _uiEffect: Channel<ChatUiEffect> = Channel()
    val uiEffect: Flow<ChatUiEffect> = _uiEffect.receiveAsFlow()

    var chatHistory = emptyList<ChatModel>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {

                    is ChatUiIntent.Init -> getChatHistory(intent.person)

                    is ChatUiIntent.SendMessage -> sendMessage(intent.message)
                }
            }
        }
    }

    private fun getChatHistory(person: Person) {
        viewModelScope.launch {
            when (
                val result = getChatHistoryUsecase(person.id)
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "getChatHistory: error ${result.error}")
                }

                is CustomResult.Success -> {
                    chatHistory = result.data
                    _state.emit(ChatUiState(chatHistory,person))
                }
            }
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            when (
                val result = sendChatMessageUsecase(
                    message = message,
                    userId = _state.value.person?.id.orEmpty()
                )
            ) {
                is CustomResult.Error -> {
                    Log.e("TAG", "sendMessage: error ${result.error}")
                }

                is CustomResult.Success -> {
                    chatHistory = result.data
                    _state.emit(ChatUiState(chatHistory,_state.value.person))
                }
            }
        }
    }

}