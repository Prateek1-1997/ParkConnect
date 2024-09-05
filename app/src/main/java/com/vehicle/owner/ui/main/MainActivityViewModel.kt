package com.vehicle.owner.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vehicle.owner.data.local.IDataStorePreference
import com.vehicle.owner.data.local.ISharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val defaultPreference: IDataStorePreference,
    private val sharedPreference: ISharedPreference,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(MainActivityUiState())
    val uiState: StateFlow<MainActivityUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isAuthenticated = sharedPreference.getAuthToken().isBlank().not(),
                isVerified = sharedPreference.isVerified()
            )
        }
    }

}