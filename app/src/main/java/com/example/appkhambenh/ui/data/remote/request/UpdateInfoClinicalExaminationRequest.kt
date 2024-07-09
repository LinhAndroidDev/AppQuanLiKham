package com.example.appkhambenh.ui.data.remote.request

data class UpdateInfoClinicalExaminationRequest(
    val circulatoryDiagnosis: String,
    val gastrointestinalDiagnosis: String,
    val maxillofacialDiagnosis: String,
    val musculoskeletalDiagnosis: String,
    val neurologicalDiagnosis: String,
    val ophthalmicDiagnosis: String,
    val otherDiagnosis: String,
    val otolaryngologicalDiagnosis: String,
    val respiratoryDiagnosis: String,
    val syndrome: String,
    val urogenitalDiagnosis: String
)