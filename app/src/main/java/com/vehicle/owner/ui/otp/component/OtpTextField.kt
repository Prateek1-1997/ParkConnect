package com.vehicle.owner.ui.otp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vehicle.owner.ui.theme.primaryColor

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int,
    onOtpTextChange: (String, Boolean) -> Unit,
    isWrong: Boolean,
    isOtpFilled: Boolean,
) {
    val focusRequester1 = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
        focusRequester1.requestFocus()
    }

    BasicTextField(
        modifier = modifier.background(Color.White).focusRequester(focusRequester1),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                        isWrong = isWrong,
                        isOtpFilled = isOtpFilled
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isWrong: Boolean,
    isOtpFilled: Boolean,
) {
    val isFocused = text.length >= index
    val char = when {
        index == text.length -> "|"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(52.dp)
            .height(52.dp)
            .border(
                1.dp, when {
                    isWrong && isOtpFilled -> Red
                    isFocused -> primaryColor
                    else -> Gray
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp)
            .background(Color.White)
            .wrapContentHeight(),
        text = char,
        color = when {
            index == text.length -> primaryColor
            else -> Color.Gray
        },
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun CharViewPreview(

) {
    CharView(
        index = 0,
        text = "1",
        isWrong = false,
        isOtpFilled = false,
    )
}


@Preview
@Composable
fun OtpTextFieldPreview() {
    OtpTextField(
        otpText = "123456",
        otpCount = 6,
        onOtpTextChange = { value, otpInputFilled ->
        },
        isWrong = false,
        isOtpFilled = true,
    )
}