package com.vehicle.owner.ui.registration

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.vehicle.owner.R
import com.vehicle.owner.core.components.TraditionalToolbar
import com.vehicle.owner.core.extension.createImageFile
import com.vehicle.owner.core.extension.createMultipartBody
import com.vehicle.owner.core.extension.getFileNameFromUri
import com.vehicle.owner.core.extension.getFilePathFromUri
import com.vehicle.owner.ui.authentication.component.PhoneNumberInputField
import com.vehicle.owner.ui.registration.component.RegistrationInputField
import com.vehicle.owner.ui.theme.primaryColor
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    uiEvent: (RegistrationUiIntent) -> Unit,
    uiState: RegistrationUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
) {
    var photoUri: Uri? by remember { mutableStateOf(null) }
    var name by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var tandCChecked by remember { mutableStateOf(false) }
    var vehicleNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.vehicle.owner" + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            photoUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TraditionalToolbar(scrollBehavior, onBack, "Create Account")
        },
        snackbarHost = {
            SnackbarHost(hostState = SnackbarHostState())
        },
    ) { values ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.car_vector),
                contentDescription = null
            )
            Text(
                text = "Hii there,\n Let's Get Started",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegistrationInputField(
                onValueChange = {
                    name = it
                },
                placeholderText = "Enter Your Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            RegistrationInputField(
                onValueChange = {
                    vehicleNumber = it
                },
                placeholderText = "Enter Your Vehicle Number",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                maxLength = 10
            )
            Spacer(modifier = Modifier.height(16.dp))
            PhoneNumberInputField(
                onValueChange = {
                    phoneNumber = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (photoUri != null) {
                Log.e("TAG", "RegistrationScreen: ${photoUri}")
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = photoUri)
                        .build()
                )
                Text(
                    text = "Your RC : ",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .width(550.dp),
                    contentScale = ContentScale.None,
                )
            } else {
                Text(
                    text = "Upload Your RC : ",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
                Button(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor.copy(alpha = 0.2f)),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image_upload),
                            contentDescription = null,
                            tint = Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Upload From Gallery",
                            style = MaterialTheme.typography.titleMedium,
                            color = Black
                        )
                    }
                }
                /*DividerText(
                    text = "or",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                Button(
                    onClick = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor.copy(alpha = 0.2f)),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null,
                            tint = Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Upload From Camera",
                            style = MaterialTheme.typography.titleMedium,
                            color = Black
                        )
                    }
                }*/
            }
            CheckboxWithTextRow(checked = tandCChecked, onCheckedChange ={tandCChecked=it}) {
             showBottomSheet=true
            }
            if(showBottomSheet){
                TandCBottomSheet(onDismiss = {showBottomSheet= false}, content = { TandCBottomSheetContent() })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    uiEvent.invoke(
                        RegistrationUiIntent.ContinueCta(
                            name,
                            vehicleNumber,
                            context.createMultipartBody(photoUri ?: Uri.EMPTY),
                            context.getFileNameFromUri(photoUri ?: Uri.EMPTY),
                            context.getFilePathFromUri(photoUri ?: Uri.EMPTY),
                            phoneNumber
                        )
                    )
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
                    Text(text = "Continue", style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegistrationScreenPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        RegistrationScreen(
            uiEvent = {},
            uiState = RegistrationUiState(),
            onBack = {},
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}