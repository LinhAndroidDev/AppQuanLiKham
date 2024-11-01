@file:Suppress("DEPRECATION")

package com.example.appkhambenh.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.util.TypedValue
import android.view.Display
import java.io.ByteArrayOutputStream
import android.util.Base64
import java.io.File
import java.io.FileOutputStream

object ConvertUtils {
    fun Int.dpToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    fun Int.pxToDp(): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), displayMetrics)
            .toInt()
    }

    fun Activity.widthDevice(): Int {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun Activity.heightDevice(): Int {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToBitmap(base64: String): Bitmap {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

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