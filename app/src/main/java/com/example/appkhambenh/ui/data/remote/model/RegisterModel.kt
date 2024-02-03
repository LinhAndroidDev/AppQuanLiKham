package com.example.appkhambenh.ui.data.remote.model

data class RegisterModel(
    val userName: String?,
    val userType: Int?,
    val userEmail: String?,
    val userPassword: String?,
    val userPhoneNumber: String?,
    val userBirthday: String?,
    val userGender: Int?,
    val hopitalID: Int? = null,
    val specialist: String? = null
)