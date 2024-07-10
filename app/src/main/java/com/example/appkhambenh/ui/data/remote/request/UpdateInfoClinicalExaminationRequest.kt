package com.example.appkhambenh.ui.data.remote.request

data class UpdateInfoClinicalExaminationRequest(
    val circulatoryDiagnosis: String,
    val respiratoryDiagnosis: String,
    val gastrointestinalDiagnosis: String,
    val urogenitalDiagnosis: String,
    val neurologicalDiagnosis: String,
    val musculoskeletalDiagnosis: String,
    val otolaryngologicalDiagnosis: String,
    val maxillofacialDiagnosis: String,
    val ophthalmicDiagnosis: String,
    val otherDiagnosis: String,
    val syndrome: String
)