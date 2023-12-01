package com.example.appkhambenh.ui.utils

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