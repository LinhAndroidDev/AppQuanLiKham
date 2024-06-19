package com.example.appkhambenh.ui.data.remote.model

data class PatientInfoModel(
    val DoB: String,
    val address: String,
    val avatar: String,
    val citizenId: String,
    val email: String,
    val fullname: String,
    val healthInsurance: String,
    val job: String?,
    val maritalStatus: String,
    val nationality: String,
    val phoneNumber: String,
    val relativeAddress: String,
    val relativeName: String,
    val relativePhone: String,
    val sex: String,
    val type: Boolean
)