package com.example.appkhambenh.ui.utils

import android.app.Activity
import android.net.Uri
import com.yalantis.ucrop.UCrop
import java.io.File

fun Activity.startCropActivity(uri: Uri) {
    val destinationUri = Uri.fromFile(File(cacheDir, "croppedImage.jpg"))
    UCrop.of(uri, destinationUri)
        .withAspectRatio(1f, 1f) // Set aspect ratio
        .withMaxResultSize(800, 800) // Set max size
        .start(this)
}