package com.example.appkhambenh.ui.data.remote.model

import com.google.gson.annotations.SerializedName

data class BookAppointmentModel(
    @SerializedName("address")
    val address: String?,

    @SerializedName("birthday")
    val birthday: Long?,

    @SerializedName("day")
    val day: Long?,

    @SerializedName("doctorId")
    val doctorId: Int?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("hospitalId")
    val hospitalId: Int?,
    @SerializedName("identification")
    val identification: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("reason")
    val reason: String?,

    @SerializedName("service")
    val service: Int?,

    @SerializedName("time")
    val time: Int?
)