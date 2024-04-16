package com.example.appkhambenh.ui.data.remote.model

import com.google.gson.annotations.SerializedName

data class Specialist(
    @SerializedName("_id")
    val id: Int? = null,

    @SerializedName("Specialist_Name")
    val name: String? = null,

    @SerializedName("Specialist_userCreate_id")
    val userCreateId: Int? = null,

    @SerializedName("Specialist_userCreate_name")
    val userCreateName: String? = null,

    @SerializedName("Specialist_userCreate_accountType")
    val accountTypeCreate: Int? = null,

    @SerializedName("Specialist_createAt")
    val createAt: Long? = null,

    @SerializedName("Specialist_statusAccept")
    val statusAccept: Int? = null,

    @SerializedName("__v")
    val v: Int? = null,
)