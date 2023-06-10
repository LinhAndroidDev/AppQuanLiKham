package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

class LoginResponse(
    override val result: Result?
) : BaseResponse<LoginResponse.Result?>(){
    data class Result(
        val id: Int?
    )
}