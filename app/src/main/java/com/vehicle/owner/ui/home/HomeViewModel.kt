package com.vehicle.owner.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.domain.usecase.GetVehicleDetailsUsecase
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
class HomeViewModel @Inject constructor(
    private val getVehicleDetailsUsecase: GetVehicleDetailsUsecase
) : ViewModel() {

    val intents: Channel<HomeUiIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState>
        get() = _state

    private val _uiEffect: Channel<HomeUiEffect> = Channel()
    val uiEffect: Flow<HomeUiEffect> = _uiEffect.receiveAsFlow()

    var searchDetails = Pair("", "")

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {

                    is HomeUiIntent.SearchVehicle ->
                        getVehicleDetails(intent.query)

                    is HomeUiIntent.OnChatCta -> _uiEffect.send(
                        HomeUiEffect.NavigateToChatScreen(
                            searchDetails.first,
                            searchDetails.second
                        )
                    )

                    else -> {}
                }
            }
        }
    }

    private suspend fun getVehicleDetails(query: String) {

        viewModelScope.launch {
            when (
                val result = getVehicleDetailsUsecase(
                    query
                )
            ) {
                is CustomResult.Error -> {

                }

                is CustomResult.Success -> {
                    searchDetails = result.data
                    _state.emit(HomeUiState.showData(result.data.second))
                }
            }
        }
    }

}