package com.example.appkhambenh.ui.data.remote.model

data class RegisterModel(
    val decentralize: List<Any>? = listOf(),
    val hopitalID: Any? = null,
    val specialist: List<Any>? = listOf(),
    val userBirthday: Any? = null,
    val userEmail: String?,
    val userGender: Any? = null,
    val userName: String?,
    val userPassword: String?,
    val userPhoneNumber: String?,
    val userType: Int?
)