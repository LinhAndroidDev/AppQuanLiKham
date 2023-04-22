package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse
import com.example.appkhambenh.ui.utils.Email

data class LoginResponse(
    val message: String,
    val statusCode: Int
    )
//): BaseResponse<List<LoginResponse.Result>?>() {
//    data class Result(
//        val name: String?,
//        val email: String?,
//        val birth: String?,
//        val phone: String?,
//        val address: String?
//    )
//}