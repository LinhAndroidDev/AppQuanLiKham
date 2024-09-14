package com.example.appkhambenh.ui.data.remote.entity

import android.os.Parcelable
import com.example.appkhambenh.ui.utils.DateUtils
import kotlinx.parcelize.Parcelize

data class PatientResponse(
    val data: ArrayList<PatientModel>,
    val totalPatient: Int,
    val paginate: Paginate
)

@Parcelize
data class PatientModel(
    val DoB: String?,
    val address: String?,
    val avatar: String?,
    val citizenId: String?,
    val countMedicalVisit: Int?,
    val createdAt: String?,
    val deletedAt: String?,
    val email: String?,
    val fullname: String?,
    val healthInsurance: String?,
    val id: Int,
    val job: String?,
    val maritalStatus: String?,
    val nationality: String?,
    val phoneNumber: String?,
    val relativeAddress: String?,
    val relativeName: String?,
    val relativePhone: String?,
    val religion: String?,
    val sex: String?,
    val type: Boolean?,
    val updatedAt: String?,
    val village: String?
) : Parcelable {
    fun getAge(): Int {
        return DateUtils.getAgeFromDate(DoB)
    }
}