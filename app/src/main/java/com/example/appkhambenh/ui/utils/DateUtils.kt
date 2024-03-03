package com.example.appkhambenh.ui.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun checkDateAppoint(strDate: String): Boolean {
    val dateFormat = SimpleDateFormat("HH:mm EEEE','dd MMMM yyyy", Locale.getDefault())
    val date = dateFormat.parse(strDate)

    val calendarCurrent = Calendar.getInstance()
    val calendarParsed = Calendar.getInstance()
    calendarParsed.time = date

    return calendarCurrent > calendarParsed
}

@SuppressLint("SimpleDateFormat")
fun convertDateToInt(date: String): Int {
    val dateTimeString = "00:00:00 $date"
    val dateFormat = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
    return (dateFormat.parse(dateTimeString)!!.time / 1000).toInt()
}

@SuppressLint("SimpleDateFormat")
fun convertIntToDate(seconds: Int): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = Date(seconds * 1000L)
    return dateFormat.format(date)
}