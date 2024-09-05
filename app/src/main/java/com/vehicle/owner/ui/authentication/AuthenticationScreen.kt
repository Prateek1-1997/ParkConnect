package com.vehicle.owner.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vehicle.owner.R
import com.vehicle.owner.core.components.DividerText
import com.vehicle.owner.ui.theme.primaryColor

@Composable
fun AuthenticationScreen(
    uiEvent: (AuthenticationUiIntent) -> Unit,
    uiState: AuthenticationUiState,
    onContinueCta: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.authentication_bg),
            contentDescription = null
        )
        Text(
            text = "India's #1 Parking \n Resolution App",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        DividerText(text = "Log in or sign up", modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        /*PhoneNumberInputField(
            onValueChange = {
                uiEvent.invoke(AuthenticationUiIntent.PhoneNumberInput(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))*/
        Button(
            onClick = {
                //uiEvent.invoke(AuthenticationUiIntent.ContinueCta)
                onContinueCta()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(12.dp).height(12.dp),
                    color = Color.White,
                    strokeWidth = 3.dp,
                )
            } else {
                Text(text = "Continue Using Google", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
fun AuthenticationScreenPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AuthenticationScreen(uiEvent = {}, uiState = AuthenticationUiState(), onContinueCta = {})
    }
}