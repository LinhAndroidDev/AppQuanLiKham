package com.example.appkhambenh.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream


object UriConvertFile {

    fun convertUriToFile(c: Context, imgUri: Uri?): File {
        // Chuyển đổi URI thành Bitmap
        val inputStream = c.contentResolver.openInputStream(imgUri!!)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Chuyển đổi Bitmap thành File
        val file = File(c.externalCacheDir, "tempFile.png") // Thay đổi tên file và định dạng file tại đây
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // Thay đổi định dạng nén tại đây
        outputStream.flush()
        outputStream.close()
        return file
    }
}