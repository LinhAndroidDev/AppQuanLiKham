package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

data class UserInfoResponse(
    override val data: Result?,
) : BaseResponse<UserInfoResponse.Result?>() {
    data class Result(
        val _id: Int,
        val name: String?,
        val mail: String?,
        val type_account: Int?,
        val status: Int?
    )
}