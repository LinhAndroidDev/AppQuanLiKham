package com.example.appkhambenh.ui.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorModel(
    @SerializedName("_id")
    val id: Int,

    @SerializedName("address")
    val address: String?,

    @SerializedName("birthday")
    val birthday: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("experience")
    val experience: String?,

    @SerializedName("gender")
    val gender: Int?,

    @SerializedName("hopitalName")
    val hospitalName: String?,

    @SerializedName("hospitalid")
    val hospitalId: Int?,

    @SerializedName("identification")
    val identification: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("role")
    val role: List<String>?,

    @SerializedName("specialist")
    val specialist: List<String>?,

    @SerializedName("startWorking")
    val startWorking: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("type")
    val type: Int?
) : Parcelable