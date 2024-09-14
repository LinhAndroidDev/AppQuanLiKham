package com.example.appkhambenh.ui.data.remote.request

data class DiagnoseRequest(
    val forecast: String,
    val principalDiagnosisCode: String,
    val prognosis: String,
    val secondaryDiagnosisCode: String,
    val treatmentPlan: String
)