package com.example.appkhambenh.ui.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TimeWorkingModel(
    @SerializedName("_id")
    val id: Int,

    @SerializedName("date")
    val date: Long?,

    @SerializedName("TimeWorking")
    val timeWorking: ArrayList<HourModel>?,

    @SerializedName("user_id")
    val userId: Int?,

    @SerializedName("hospitalId")
    val hospitalId: Int?
)

@Parcelize
data class HourModel(
    @SerializedName("_id")
    val id: Int,

    @SerializedName("time_working")
    val hour: String
) : Parcelable