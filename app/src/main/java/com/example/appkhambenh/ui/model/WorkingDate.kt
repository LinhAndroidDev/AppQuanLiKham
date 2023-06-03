package com.example.appkhambenh.ui.model

import com.example.appkhambenh.ui.data.remote.entity.TimeResponse

data class WorkingDate(
    val id_day: Int,
    val list_working: ArrayList<TimeResponse>
)