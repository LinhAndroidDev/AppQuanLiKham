package com.example.appkhambenh.ui.data.remote.model

data class AccountModel(
    val avatar: String?,
    val createdAt: String,
    val deletedAt: String?,
    val email: String?,
    val fullname: String?,
    val id: Int,
    val password: String,
    val role: Role,
    val roleId: Int,
    val status: Boolean,
    val updatedAt: String
)

data class Role(
    val id: Int,
    val name: String
)