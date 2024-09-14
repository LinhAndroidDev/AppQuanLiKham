package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.ConfirmAppointResponse
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    suspend fun getListAppointment(time: String? = null): Flow<AppointmentResponse>

    suspend fun confirmAppoint(id: Int): Flow<ConfirmAppointResponse>
}