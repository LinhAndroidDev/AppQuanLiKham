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
    val forecast: String?,
    val gastrointestinalDiagnosis: String,
    val glu: String?,
    val hb: String?,
    val height: Int,
    val htc: String?,
    val id: Int,
    val image: Any,
    val imageAnalysisType: Any,
    val images: Any,
    val imagesReview: Any,
    val info: Any,
    val lym: String?,
    val maxillofacialDiagnosis: String,
    val mch: String?,
    val mcv: String?,
    val medicalHistoryId: Int,
    val mono: String?,
    val musculoskeletalDiagnosis: String,
    val neurologicalDiagnosis: String,
    val neut: String?,
    val ophthalmicDiagnosis: String,
    val otherDiagnosis: String,
    val otolaryngologicalDiagnosis: String,
    val plt: String?,
    val principalDiagnosisCode: String?,
    val prognosis: String?,
    val pulse: String?,
    val rbc: String?,
    val respiratoryDiagnosis: String,
    val secondaryDiagnosisCode: String?,
    val serviceId: Int,
    val status: String,
    val syndrome: String,
    val systolic: Int,
    val temperature: Int,
    val treatmentPlan: String?,
    val updatedAt: String,
    val ure: String?,
    val urogenitalDiagnosis: String,
    val wbc: String?,
    val weight: Int,
    val list_image: ArrayList<ImageModel>?
)

data class ImageModel(
    val id: Int,
    val path: String,
    val type: Int,
    val service_order: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)