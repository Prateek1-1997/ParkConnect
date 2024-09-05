package com.vehicle.owner.ui.home

sealed class HomeUiState {
     data class showData(val vehicleNumber: String): HomeUiState()
    object Idle : HomeUiState()


}

sealed class HomeUiEffect {
    data class ShowError(val message: String) : HomeUiEffect()
    object NavigateToSearchScreen : HomeUiEffect()
    data class NavigateToChatScreen(val userId: String, val vehicleNumber: String): HomeUiEffect()
}

sealed class HomeUiIntent {

    data class SearchVehicle(val query:String) : HomeUiIntent()
    object OnChatCta: HomeUiIntent()
}