package com.vehicle.owner.ui.home

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.vehicle.owner.core.location.CustomCurrentLocationContent
import com.vehicle.owner.core.location.CustomLocationScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeRoute(
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToChatScreen: (String, String) -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val latitude = remember {
        mutableDoubleStateOf(0.0)
    }
    val longitude = remember {
        mutableDoubleStateOf(0.0)
    }
    val isLocationEnabled = remember {
        mutableStateOf(false)
    }
    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            isLocationEnabled.value = true
        }
        else {
        }
    }

    BackHandler {
        onBack()
    }

    LaunchedEffect("side_effect") {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is HomeUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        effect.message,
                    )
                }

                is HomeUiEffect.NavigateToChatScreen -> onNavigateToChatScreen(
                    effect.vehicleNumber,
                    effect.userId
                )

                is HomeUiEffect.NavigateToSearchScreen -> onNavigateToSearchScreen()
            }
        }.collect()
    }
    val fineLocationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
        ),
        onPermissionsResult = {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@rememberMultiplePermissionsState
            }
        }
    )
    if (fineLocationPermissionState.allPermissionsGranted) {
        CustomCurrentLocationContent(
            fineLocationPermissionState.allPermissionsGranted
        ) {
            latitude.doubleValue = it.latitude
            longitude.doubleValue = it.longitude
        }
        checkLocationSetting(context, onEnabled = {
            isLocationEnabled.value = true
        }, onDisabled = {
            settingResultRequest.launch(it)
        })
        HomeScreen(
            uiEvent = { intent ->
                coroutineScope.launch {
                    viewModel.intents.send(intent)
                }
            },
            uiState = uiState,
            scrollBehavior = scrollBehavior,
            onBack = onBack,
            longitude = longitude.doubleValue,
            latitude = latitude.doubleValue,
        )
    } else {
        CustomLocationScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp), fineLocationPermissionState = fineLocationPermissionState
        )
    }
}

fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit
) {

    val locationRequest = LocationRequest.create().apply {
        interval = 100
        fastestInterval = 100
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener { onEnabled() }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution)
                    .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // ignore here
            }
        }
    }

}