package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse

class UploadAvatarResponse(
    override val data: String?
) : BaseResponse<String?>()

class GetAvatarResponse(
    override val data: String?
) : BaseResponse<String?>()