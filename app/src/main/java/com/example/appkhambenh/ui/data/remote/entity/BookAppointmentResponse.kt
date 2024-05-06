package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

data class BookAppointmentResponse(override val data: Result?) :
    BaseResponse<BookAppointmentResponse.Result>() {
    data class Result(
        val content: ArrayList<DetailAppointment>?,
        val page: Int,
        val size: Int,
        val pagesize: Int,
        val totalElement: Int,
        val total_page: Int
    )
}

data class DetailAppointment(
    val _id: Int?,
    val doctorId: Int?,
    val doctorName: String?,
    val hospitalId: Int?,
    val hospitalName: String?,
    val patientAdress: String?,
    val patientBirthday: Int?,
    val patientId: String?,
    val patientName: String?,
    val patientPhoneNumber: String?,
    val patientemail: String?,
    val reason: String?,
    val serviceId: Int?,
    val serviceName: String?,
    val status: Int?,
    val time: String?,
    val day: Long?
)