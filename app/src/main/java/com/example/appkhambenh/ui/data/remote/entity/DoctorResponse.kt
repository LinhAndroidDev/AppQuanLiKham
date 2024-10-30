package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse
import com.example.appkhambenh.ui.data.remote.model.DoctorModel

data class DoctorResponse(
    override val data: Result,
) : BaseResponse<DoctorResponse.Result>() {
    class Result(
        val content: ArrayList<DoctorModel>,
        val page: String,
        val size: String,
        val pagesize: Int,
        val totalElement: Int,
        val total_page: Int,
    )
}