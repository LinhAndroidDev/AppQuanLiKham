package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

class ProfileResponse(
    override val data: Result?
) : BaseResponse<ProfileResponse.Result>() {
    class Result(
        val DOB: Any?,
        val __v: Int?,
        val _id: Int?,
        val address: String?,
        val calendarWorking: List<Any>?,
        val decentrialize: List<Int>?,
        val district: String?,
        val email: String?,
        val experient: String?,
        val gender: Int?,
        val hospitalId: Any?,
        val identification: String?,
        val name: String?,
        val password: String?,
        val phoneNumber: String?,
        val province: String?,
        val specialistId: List<Int>?,
        val startWorking: Long?,
        val status: Int?,
        val type: Int?,
        val ward: String?
    )
}