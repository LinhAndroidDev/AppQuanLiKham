package com.example.appkhambenh.ui.data.remote.model

data class LoginModel(
    val email: String?,
    val password: String?,
    val userType: Int? = 0
)