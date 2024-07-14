package com.example.appkhambenh.ui.data.remote.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MedicalHistoryResponse(
    val data: List<Data>,
    val paginate: Paginate
) {
    @Parcelize
    data class Data(
        val bed: String?,
        val createdAt: String,
        val deletedAt: String?,
        val diagnoseMove: String?,
        val diagnoseNow: String?,
        val diagnosePast: String?,
        val doctor: DoctorPK?,
        val doctorId: Int?,
        val emergencyDiagnose: String?,
        val facultyTreatment: String?,
        val hospitalDischarge: String?,
        val id: Int,
        val introductionPlace: String?,
        val patientId: Int,
        val patient: PatientModel?,
        val reason: String?,
        val room: String?,
        val updatedAt: String
    ): Parcelable
}

@Parcelize
data class DoctorPK(
    val avatar: String?,
    val createdAt: String,
    val deletedAt: String?,
    val email: String,
    val fullname: String?,
    val id: Int,
    val password: String,
    val roleId: Int,
    val status: Boolean,
    val updatedAt: String
) : Parcelable