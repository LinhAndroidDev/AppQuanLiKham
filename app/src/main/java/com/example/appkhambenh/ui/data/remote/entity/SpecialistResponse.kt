package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.base.BaseResponse
import com.example.appkhambenh.ui.data.remote.model.Specialist

data class SpecialistResponse(
    override val data: ArrayList<Specialist>?
) : BaseResponse<ArrayList<Specialist>?>()