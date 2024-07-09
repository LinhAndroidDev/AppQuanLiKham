package com.example.appkhambenh.ui.data.remote.entity

data class ServiceOrderResponse(
    val data: ArrayList<ServiceOrderModel>
)

data class ServiceOrderModel(
    val bloodGlucose: Int,
    val bloodGroup: String?,
    val circulatoryDiagnosis: String?,
    val createdAt: String,
    val deletedAt: String?,
    val diastolic: Int,
    val forecast: Any,
    val gastrointestinalDiagnosis: String,
    val glu: Any,
    val hb: Any,
    val height: Int,
    val htc: Any,
    val id: Int,
    val image: Any,
    val imageAnalysisType: Any,
    val images: Any,
    val imagesReview: Any,
    val info: Any,
    val lym: Any,
    val maxillofacialDiagnosis: String,
    val mch: Any,
    val mcv: Any,
    val medicalHistoryId: Int,
    val mono: Any,
    val musculoskeletalDiagnosis: String,
    val neurologicalDiagnosis: String,
    val neut: Any,
    val ophthalmicDiagnosis: String,
    val otherDiagnosis: String,
    val otolaryngologicalDiagnosis: String,
    val plt: Any,
    val principalDiagnosisCode: Any,
    val prognosis: Any,
    val pulse: Int,
    val rbc: Any,
    val respiratoryDiagnosis: String,
    val secondaryDiagnosisCode: Any,
    val serviceId: Int,
    val status: String,
    val syndrome: String,
    val systolic: Int,
    val temperature: Int,
    val treatmentPlan: Any,
    val updatedAt: String,
    val ure: Any,
    val urogenitalDiagnosis: String,
    val wbc: Any,
    val weight: Int
)