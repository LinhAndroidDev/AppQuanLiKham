package com.example.appkhambenh.ui.data.remote.entity

data class AddAccountResponse(
    val avatar: Any,
    val createdAt: String,
    val deletedAt: Any,
    val email: String,
    val fullname: Any,
    val id: Int,
    val password: String,
    val roleId: Int,
    val status: Boolean,
    val updatedAt: String,
    val user: User
) {
    data class User(
        val avatar: String?,
        val email: String,
        val id: Int,
        val role: String
    )
}