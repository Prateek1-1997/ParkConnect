package com.vehicle.owner.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxWithTextRow(checked: Boolean, onCheckedChange: (Boolean) -> Unit,onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color.White).padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(text = "I agree to the Terms and Conditions",  style = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        ),
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
fun PreviewCheckboxWithTextRow() {
    CheckboxWithTextRow(
        checked = true,
        onCheckedChange = {},
        onClick = {}
    )
}