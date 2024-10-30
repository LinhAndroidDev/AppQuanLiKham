package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

data class LoginResponse(
    override val data: Result?,
) : BaseResponse<LoginResponse.Result?>() {
    data class Result(
        val token: String?,
    )
}