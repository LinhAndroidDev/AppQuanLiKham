package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse
import com.example.appkhambenh.ui.data.remote.model.UserModel

data class UserInfoResponse(
    override val data: UserModel?,
) : BaseResponse<UserModel?>()