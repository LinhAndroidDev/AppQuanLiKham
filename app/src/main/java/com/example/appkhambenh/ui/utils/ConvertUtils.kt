package com.example.appkhambenh.ui.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

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
}