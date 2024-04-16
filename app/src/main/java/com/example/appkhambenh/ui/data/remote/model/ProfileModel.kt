package com.example.appkhambenh.ui.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("NgheNghip")
    val NgheNghip: String?,

    @SerializedName("address")
    val address: String,

    @SerializedName("dan_toc")
    val dan_toc: String?,

    @SerializedName("decentrialize")
    val decentrialize: List<Int>? = listOf(2),

    @SerializedName("district")
    val district: String? = "Hà Đông",

    @SerializedName("experient")
    val experient: String? = "1 năm kinh nghiệm",

    @SerializedName("gender")
    val gender: Int? = 1,

    @SerializedName("hopitalID")
    val hopitalID: Int? = null,

    @SerializedName("hopitalId")
    val hopitalId: Int? = null,

    @SerializedName("identification")
    val identification: String?,

    @SerializedName("province")
    val province: String? = "Hà Nội",

    @SerializedName("quoc_tich")
    val quoc_tich: String?,

    @SerializedName("specialist")
    val specialist: List<Int>? = listOf(2),

    @SerializedName("startWorking")
    val startWorking: Long? = 123345345345345,

    @SerializedName("userBirthday")
    val userBirthday: Long?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("userGender")
    val userGender: Int?,

    @SerializedName("userName")
    val userName: String?,

    @SerializedName("userPhoneNumber")
    val userPhoneNumber: String?,

    @SerializedName("userType")
    val userType: Int? = 0,

    @SerializedName("ward")
    val ward: String? = "Dương Nội"
)