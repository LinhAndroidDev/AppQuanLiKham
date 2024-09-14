package com.example.appkhambenh.ui.data.remote.entity

data class AppointmentResponse(
    val data: ArrayList<StatePatient>,
    val paginate: Paginate,
    val totalAppointment: Int
)

data class StatePatient(
    val createdAt: String,
    val deletedAt: String?,
    val id: Int,
    val introductionPlace: String,
    val patient: PatientModel,
    val patientId: Int,
    val reason: String,
    val status: String,
    val time: String,
    val updatedAt: String
)