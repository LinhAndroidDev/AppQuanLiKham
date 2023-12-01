package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

data class UserInfoResponse(
    override val result: Result?,
) : BaseResponse<UserInfoResponse.Result?>() {
    data class Result(
        val id: Int?,
        val name: String?,
        val email: String?,
        val sex: Int?,
        val birth: String?,
        val phone: String?,
        val address: String?,
        val avatar: String?,
        val type: Int?,
    )
}