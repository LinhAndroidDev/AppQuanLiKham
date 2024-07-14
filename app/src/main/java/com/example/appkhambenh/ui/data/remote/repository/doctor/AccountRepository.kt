package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AccountRepository @Inject constructor(private val doctorService: DoctorService) {
    suspend fun getAccount(fullname: String? = null, email: String? = null, roleId: Int? = null) =
        doctorService.getAccount(fullname, email, roleId)

    suspend fun addAccount(addAccountRequest: AddAccountRequest) = doctorService.addAccount(addAccountRequest)
}