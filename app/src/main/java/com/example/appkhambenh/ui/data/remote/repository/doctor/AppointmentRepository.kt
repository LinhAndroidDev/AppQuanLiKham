package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.ConfirmAppointResponse
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    suspend fun getListAppointment(time: String? = null): Flow<ApiState<AppointmentResponse>>

    suspend fun confirmAppoint(id: Int): Flow<ApiState<ConfirmAppointResponse>>
}