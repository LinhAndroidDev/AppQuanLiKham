package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class AccountRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    AccountRepository {
    override suspend fun getAccount(fullname: String?, email: String?, roleId: Int?) = flow {
        emit(doctorService.getAccount(fullname, email, roleId))
    }

    override suspend fun addAccount(addAccountRequest: AddAccountRequest) = flow {
        emit(doctorService.addAccount(addAccountRequest))
    }
}