package com.example.appkhambenh.ui.data.remote.repository

import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.model.BookAppointmentModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AppointmentRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun createBooking(
        bookAppointmentModel: BookAppointmentModel
    ) = apiService.createBooking(bookAppointmentModel)

    suspend fun getListBooking() = apiService.getListBooking()
}