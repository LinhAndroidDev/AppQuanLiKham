package com.example.appkhambenh.ui.data.remote.entity

data class ServiceOrderResponse(
    val data: ArrayList<ServiceOrderModel>
)

data class ServiceOrderModel(
    val bloodGlucose: Int,
    val bloodGroup: Any,
    val circulatoryDiagnosis: Any,
    val createdAt: String,
    val deletedAt: Any,
    val diastolic: Int,
    val forecast: Any,
    val gastrointestinalDiagnosis: Any,
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
    val maxillofacialDiagnosis: Any,
    val mch: Any,
    val mcv: Any,
    val medicalHistoryId: Int,
    val mono: Any,
    val musculoskeletalDiagnosis: Any,
    val neurologicalDiagnosis: Any,
    val neut: Any,
    val ophthalmicDiagnosis: Any,
    val otherDiagnosis: Any,
    val otolaryngologicalDiagnosis: Any,
    val plt: Any,
    val principalDiagnosisCode: Any,
    val prognosis: Any,
    val pulse: Int,
    val rbc: Any,
    val respiratoryDiagnosis: Any,
    val secondaryDiagnosisCode: Any,
    val serviceId: Int,
    val status: String,
    val syndrome: Any,
    val systolic: Int,
    val temperature: Int,
    val treatmentPlan: Any,
    val updatedAt: String,
    val ure: Any,
    val urogenitalDiagnosis: Any,
    val wbc: Any,
    val weight: Int
)