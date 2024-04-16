@file:Suppress("DEPRECATION")

package com.example.appkhambenh.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.Display

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

    fun widthDevice(activity: Activity): Int {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun heightDevice(activity: Activity): Int {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }
}