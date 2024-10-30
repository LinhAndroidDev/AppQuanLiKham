package com.example.appkhambenh.ui.data.remote.entity

data class DoctorLoginResponse(
    val auth: Auth?,
    val user: InfoUser?,
    val error: String?
)

data class Auth(
    val tokenType: String,
    val accessToken: String,
    val refreshToken: String,
)

data class InfoUser(
    val id: Int,
    val roleId: Int,
    val email: String?,
    val fullname: String?,
    val avatar: String?,
    val status: Boolean?,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?
)