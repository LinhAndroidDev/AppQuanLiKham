package com.example.appkhambenh.ui.model

data class Province(
    val name: String,
    val code: Int,
    val districts: ArrayList<District>?
)