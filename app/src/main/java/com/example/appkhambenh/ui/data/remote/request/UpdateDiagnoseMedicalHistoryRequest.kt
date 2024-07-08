package com.example.appkhambenh.ui.data.remote.request

data class UpdateDiagnoseMedicalHistoryRequest(
    val diagnosePast: String?,
    val diagnoseNow: String?,
    val diagnoseMove: String?,
    val emergencyDiagnose: String?
)