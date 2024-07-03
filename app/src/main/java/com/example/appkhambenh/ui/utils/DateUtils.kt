@file:Suppress("DEPRECATION")

package com.example.appkhambenh.ui.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    const val TIME = "HH:mm:ss dd/MM/yyyy"
    const val DAY_OF_YEAR = "yyyy-MM-dd"
    const val TIME_UPLOAD_AVATAR = "dd_MM_yyyy_HH_mm_ss"
    const val MINUTES = "mm:ss"
    const val DATE_FROM_VIET_NAM = "'Ngày' dd 'tháng' MM 'năm' yyyy"

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

    fun getDateCurrentFromVietNam(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(DATE_FROM_VIET_NAM, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getAgeFromDate(dateString: String?): Int {
        val calendar = Calendar.getInstance()
        // Phân tích cú pháp chuỗi ngày tháng
        val zonedDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)

        return calendar.get(Calendar.YEAR) - zonedDateTime.year
    }

    fun convertIsoDateTimeToDate(isoDateTime: String): String {
        val dateTime = LocalDateTime.parse(isoDateTime, DateTimeFormatter.ISO_DATE_TIME)
        val dateFormatter = DateTimeFormatter.ofPattern(DAY_OF_YEAR)
        return dateTime.format(dateFormatter)
    }

    fun convertDateToIsoDateTime(date: String): String {
        val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DAY_OF_YEAR))
        val localDateTime = localDate.atStartOfDay()
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}