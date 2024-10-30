package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.base.BaseRepository
import com.example.appkhambenh.ui.data.remote.entity.AccountResponse
import com.example.appkhambenh.ui.data.remote.entity.AddAccountResponse
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class AccountRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    AccountRepository, BaseRepository() {
    override suspend fun getAccount(
        fullname: String?,
        email: String?,
        roleId: Int?,
    ): Flow<ApiState<AccountResponse>> = safeApiCall {
        doctorService.getAccount(fullname, email, roleId)
    }

    override suspend fun addAccount(addAccountRequest: AddAccountRequest): Flow<ApiState<AddAccountResponse>> =
        safeApiCall {
            doctorService.addAccount(addAccountRequest)
        }
}