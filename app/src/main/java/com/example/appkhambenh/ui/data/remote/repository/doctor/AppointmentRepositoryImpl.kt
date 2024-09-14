package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.ConfirmAppointResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class AppointmentRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    AppointmentRepository {
    override suspend fun getListAppointment(time: String?): Flow<AppointmentResponse> = flow {
        emit(doctorService.getListAppoint(time))
    }

    override suspend fun confirmAppoint(id: Int): Flow<ConfirmAppointResponse> = flow {
        emit(doctorService.confirmAppoint(id))
    }
}