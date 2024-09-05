package com.vehicle.owner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.vehicle.owner.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Montserrat")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

val CustomTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 58.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily, fontSize = 46.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily, fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily, fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily, fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily, fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily, fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily, fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily, fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily, fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily, fontSize = 12.sp
    )
)