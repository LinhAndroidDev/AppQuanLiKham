package com.example.appkhambenh.ui.data.remote.model

data class UserModel(
    val _id: Int,
    val address: String?,
    val avatar: String?,
    val birthday: Long?,
    val createAt: String?,
    val decentralizes: List<Any>?,
    val district: String?,
    val email: String?,
    val experient: String?,
    val gender: Int?,
    val hospitalId: Int?,
    val identification: String?,
    val name: String?,
    val phoneNumber: String?,
    val province: String?,
    val specialists: List<Any>,
    val startWorking: String?,
    val status: Int?,
    val type: Int?,
    val ward: String?
)