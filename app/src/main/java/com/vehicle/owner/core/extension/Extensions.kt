package com.vehicle.owner.core.extension

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

fun Context.getRealPathFromURI(uri: Uri): String {
    var result = ""
    val cursor = this.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        val idx = it.getColumnIndex("_data")
        result = it.getString(idx)
    }
    return result
}

fun Context.createMultipartBody(uri: Uri): MultipartBody.Part {
    val file = File(this.getRealPathFromURI(uri))
    val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
    return MultipartBody.Part.createFormData("file", file.name, requestFile)
}

fun Context.getFileNameFromUri(uri: Uri): String? {
    var fileName: String? = null
    this.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            fileName = cursor.getString(displayNameIndex)
        }
    }
    return fileName
}

fun Context.getFilePathFromUri(uri: Uri): String? {
    var filePath: String? = null
    val projection = arrayOf("_data")
    this.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow("_data")
            filePath = cursor.getString(columnIndex)
        }
    }
    return filePath
}

fun getTimeFormatted(timestamp: String) : String {
    try {
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("d'${getDaySuffix(timestamp.substring(8, 10).toInt())}' MMM yyyy, h:mm a")
        } else {
            return timestamp
        }
        val dateTime = LocalDateTime.parse(timestamp.substring(0, timestamp.indexOf(".")), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        return dateTime.format(formatter)
    } catch (e: Exception) {
        return timestamp
    }
}

fun getDaySuffix(day: Int): String {
    return when (day) {
        1, 21, 31 -> "st"
        2, 22 -> "nd"
        3, 23 -> "rd"
        else -> "th"
    }
}