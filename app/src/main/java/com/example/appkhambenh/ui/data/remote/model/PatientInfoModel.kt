package com.example.appkhambenh.ui.data.remote.model

//data class PatientInfoModel(
//    val DoB: String?,
//    val address: String?,
//    val avatar: String?,
//    val citizenId: String?,
//    val email: String?,
//    val fullname: String?,
//    val healthInsurance: String?,
//    val job: String?,
//    val maritalStatus: String?,
//    val nationality: String?,
//    val phoneNumber: String?,
//    val relativeAddress: String?,
//    val relativeName: String?,
//    val relativePhone: String?,
//    val sex: String?,
//    val type: Boolean?
//)

data class PatientInfoModel(
    val DoB: String? = "2001-08-07",
    val address: String? = "Hà Nội",
    val avatar: String? = "",
    val citizenId: String? = "011211012123",
    val email: String? = "linh@gmail.com",
    val fullname: String? = "Nguyen Huu Linh",
    val healthInsurance: String? = "12635436437",
    val job: String? = "Không có",
    val maritalStatus: String? = "0",
    val nationality: String? = "Việt Nam",
    val phoneNumber: String? = "0969601767",
    val relativeAddress: String? = "abc",
    val relativeName: String? = "abc",
    val relativePhone: String? = "0977217934",
    val sex: String? = "0",
    val type: Boolean? = false
)