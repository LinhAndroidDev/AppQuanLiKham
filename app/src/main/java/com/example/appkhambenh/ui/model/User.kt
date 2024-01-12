package com.example.appkhambenh.ui.model

data class Member(
    val name: String?,
    val avatar: String?
)

data class User(
    val id: Int?,
    val name: String?,
    val email: String?,
    val sex: Int?,
    val birth: String?,
    val phone: String?,
    val address: String?,
    val avatar: String?,
    val type: Int?
)