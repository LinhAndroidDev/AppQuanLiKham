package com.example.appkhambenh.ui.model

data class District(
    val name: String,
    val code: Int,
    val wards: ArrayList<Ward>
)