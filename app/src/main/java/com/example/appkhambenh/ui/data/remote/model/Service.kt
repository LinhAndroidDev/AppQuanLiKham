package com.example.appkhambenh.ui.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    @SerializedName("Specialist_Name")
    val nameSpecial: String,

    @SerializedName("Specialist_createAt")
    val createAt: Long,

    @SerializedName("Specialist_statusAccept")
    val statusAccept: Int,

    @SerializedName("Specialist_userCreate_accountType")
    val accountType: Int,

    @SerializedName("Specialist_userCreate_id")
    val userCreateId: Int,

    @SerializedName("Specialist_userCreate_name")
    val userCreateName: String,

    @SerializedName("__v")
    val v: Int,

    @SerializedName("_id")
    val id: Int
): Parcelable