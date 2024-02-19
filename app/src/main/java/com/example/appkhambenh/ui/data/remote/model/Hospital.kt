package com.example.appkhambenh.ui.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hospital(
    @SerializedName("_id")
    val id: Int,
    val address: Any,
    val email: String,
    val identification: Any,
    val name: String,
    val phone: String,
    val specialist: ArrayList<Specialist>,
    val status: Int
): Serializable