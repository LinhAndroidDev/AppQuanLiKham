package com.example.appkhambenh.ui.data.remote.model

data class MedicineModel(
    val id: Int,
    val name: String?,
    val drugCode: Long? = null,
    val uses: String? = null,
    val dosage: String? = null,
    val dosageForms: String? = null,
    val expiry: String? = null,
    val producer: String? = null,
    val price: Int? = null,
    val inventoryNumber: Int?,
    val updateDay: String?
)