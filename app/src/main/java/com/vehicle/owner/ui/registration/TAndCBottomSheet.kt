package com.vehicle.owner.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vehicle.owner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TandCBottomSheet(
    onDismiss: () -> Unit,
    content: @Composable () -> (Unit),
    modifier: Modifier = Modifier,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()


    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {},
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        ) {
            IconButton(
                onClick = onDismiss,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_app_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                },
            )
        }
        content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, widthDp = 420, heightDp = 1000)
@Composable
fun BottomSheetPreview() {
    TandCBottomSheet(
        onDismiss = {},
        content = {},
    )
}