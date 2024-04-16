package com.example.appkhambenh.ui.model

import android.os.Parcelable
import com.example.appkhambenh.ui.data.remote.model.HourModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    val time: String?,
    val day: String?,
    val hours: ArrayList<HourModel>?,
    val date: String?
) : Parcelable