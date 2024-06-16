package com.example.appkhambenh.ui.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val TIME = "HH:mm:ss dd/MM/yyyy"
    const val DAY_OF_YEAR = "dd/MM/yyyy"
    const val TIME_UPLOAD_AVATAR = "dd_MM_yyyy_HH_mm_ss"
    const val MINUTES = "mm:ss"

    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String): Long {
        val dateTimeString = "00:00:00 $date"
        val dateFormat = SimpleDateFormat(TIME)
        return (dateFormat.parse(dateTimeString)!!.time / 1000)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDate(seconds: Long): String {
        val dateFormat = SimpleDateFormat(DAY_OF_YEAR)
        val date = Date(seconds * 1000L)
        return dateFormat.format(date)
    }

    fun getDateCurrentToLong(): Long {
        val dateCurrent = SimpleDateFormat(DAY_OF_YEAR, Locale.getDefault()).format(Date())
        return convertDateToLong(dateCurrent)
    }

    fun getTimeCurrent(): String {
        val timeCurrent = SimpleDateFormat(TIME_UPLOAD_AVATAR, Locale.getDefault()).format(Date())
        return timeCurrent.toString()
    }
}