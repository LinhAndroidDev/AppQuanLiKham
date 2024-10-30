package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse
import com.example.appkhambenh.ui.data.remote.model.TimeWorkingModel

data class TimeWorkingResponse(
    override val data: Result?,
) : BaseResponse<TimeWorkingResponse.Result?>() {
    data class Result(
        val content: ArrayList<TimeWorkingModel>?,
        val total_record: Int?,
    )
}