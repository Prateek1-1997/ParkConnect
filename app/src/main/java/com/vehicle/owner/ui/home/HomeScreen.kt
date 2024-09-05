package com.vehicle.owner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiEvent: (HomeUiIntent) -> Unit,
    uiState: HomeUiState,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    latitude: Double,
    longitude: Double,
) {
    val uiSettings = remember {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = false,
                mapToolbarEnabled = false,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = true,
                scrollGesturesEnabled = true,
                scrollGesturesEnabledDuringRotateOrZoom = true,
                myLocationButtonEnabled = true,
            )
        )
    }
    val properties = remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 20f)
    }
    LaunchedEffect(latitude,longitude) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 20f)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            cameraPositionState = cameraPositionState,
            properties = properties.value,
            uiSettings = uiSettings.value,
        ) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
        var isBottomSheetVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BottomSheetContent(uiState, onSearch = {
                uiEvent(HomeUiIntent.SearchVehicle(it))

            }, onItemClick = {
                uiEvent(HomeUiIntent.OnChatCta)
            })
        }

    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//fun HomeScreenPreview() {
//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        HomeScreen(
//            uiEvent = {},
//            uiState = {},
//            onBack = {},
//            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
//            latitude = 0.0,
//            longitude = 0.0,
//        )
//    }
//}