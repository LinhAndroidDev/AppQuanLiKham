package com.example.appkhambenh.ui.model

data class Address(
    val name: String,
    val slug: String,
    val type: String,
    val name_with_type: String,
    val code: String,
    var path: String? = null,
    var path_with_type: String? = null,
    var parent_code: String? = null
) {
    fun getDistrictByCodeProvince(codeProvince: String): Boolean {
        return codeProvince == parent_code
    }

    fun getWardByCodeDistrict(codeDistrict: String): Boolean {
        return codeDistrict == parent_code
    }
}