package com.example.appkhambenh.ui.data.remote.entity

data class GetMedicalHistoryResponse(
    val data: Data
) {
    data class Data(
        val bed: String?,
        val createdAt: String,
        val deletedAt: String?,
        val diagnoseMove: String,
        val diagnoseNow: String,
        val diagnosePast: String,
        val doctor: DoctorPK?,
        val doctorId: Int?,
        val emergencyDiagnose: String,
        val facultyTreatment: String?,
        val hospitalDischarge: String?,
        val id: Int,
        val introductionPlace: String,
        val patientId: Int,
        val patient: PatientModel?,
        val reason: String,
        val room: String?,
        val updatedAt: String
    )
}