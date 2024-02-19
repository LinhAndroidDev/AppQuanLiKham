package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse
import com.example.appkhambenh.ui.data.remote.model.Hospital

data class HospitalResponse(
    override val data: Result,
) : BaseResponse<HospitalResponse.Result>() {
    data class Result(
        val content: ArrayList<Hospital>,
        val page: Int,
        val size: Int,
        val pageSize: Int,
        val totalElement: Int,
        val total_page: Int,
    )
}