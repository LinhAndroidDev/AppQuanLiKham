package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.base.BaseRepository
import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.ConfirmAppointResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class AppointmentRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    AppointmentRepository, BaseRepository() {
    override suspend fun getListAppointment(time: String?): Flow<ApiState<AppointmentResponse>> =
        safeApiCall {
            doctorService.getListAppoint(time)
        }

    override suspend fun confirmAppoint(id: Int): Flow<ApiState<ConfirmAppointResponse>> =
        safeApiCall {
            doctorService.confirmAppoint(id)
        }
}