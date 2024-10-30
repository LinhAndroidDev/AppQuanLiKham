package com.example.appkhambenh.ui.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hospital(
    @SerializedName("_id")
    val id: Int,
    val address: String?,
    val email: String?,
    val identification: String?,
    val name: String,
    val phone: String?,
    val specialist: ArrayList<Service>,
    val status: Int,
    val district: String?,
    val ward: String?,
    val province: String?,
    @SerializedName("DOB")
    val dob: String?,
) : Parcelable