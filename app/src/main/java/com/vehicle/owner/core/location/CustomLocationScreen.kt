package com.vehicle.owner.core.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.vehicle.owner.R
import com.vehicle.owner.ui.theme.primaryColor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CustomLocationScreen(
    modifier: Modifier = Modifier,
    fineLocationPermissionState: MultiplePermissionsState,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "To provide you with the best service, we kindly ask for your location.",
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Image(
            painter = painterResource(id = R.drawable.location_on),
            contentDescription = null
        )
        Button(
            onClick = {
                fineLocationPermissionState.launchMultiplePermissionRequest()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
        ) {
            Text(text = "Provide Location Permission", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview
@Composable
fun CustomLocationScreenPreview() {
    /*CustomLocationScreen(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    )*/
}