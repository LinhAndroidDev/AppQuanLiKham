package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.entity.AccountResponse
import com.example.appkhambenh.ui.data.remote.entity.AddAccountResponse
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getAccount(fullname: String? = null, email: String? = null, roleId: Int? = null): Flow<AccountResponse>

    suspend fun addAccount(addAccountRequest: AddAccountRequest): Flow<AddAccountResponse>
}