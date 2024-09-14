package com.example.appkhambenh.ui.data.remote.request

data class AddMedicalHistoryRequest(
    val reason: String = "",
    val introductionPlace: String = "",
    val patientId: Int,
)