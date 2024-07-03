package com.example.appkhambenh.ui.data.remote.entity

data class MedicalHistoryResponse(
    val data: List<Data>,
    val paginate: Paginate
) {
    data class Data(
        val bed: Any,
        val createdAt: String,
        val deletedAt: Any,
        val diagnoseMove: String,
        val diagnoseNow: String,
        val diagnosePast: String,
        val doctor: DoctorPK,
        val doctorId: Int,
        val emergencyDiagnose: String,
        val facultyTreatment: Any,
        val hospitalDischarge: String,
        val id: Int,
        val introductionPlace: String,
        val patientId: Int,
        val reason: String,
        val room: Any,
        val updatedAt: String
    )
}

data class DoctorPK(
    val avatar: Any,
    val createdAt: String,
    val deletedAt: Any,
    val email: String,
    val fullname: Any,
    val id: Int,
    val password: String,
    val roleId: Int,
    val status: Boolean,
    val updatedAt: String
)