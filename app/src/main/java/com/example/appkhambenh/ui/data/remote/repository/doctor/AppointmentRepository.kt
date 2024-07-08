package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.request.ConfirmAppointRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AppointmentRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getListAppointment() = doctorService.getListAppoint()

    suspend fun confirmAppoint(id: Int) = doctorService.confirmAppoint(id)
}