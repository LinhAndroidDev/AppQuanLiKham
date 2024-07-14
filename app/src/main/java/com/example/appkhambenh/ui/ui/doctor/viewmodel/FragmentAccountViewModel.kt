package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.AccountModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.AccountRepository
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAccountViewModel @Inject constructor(private val accountRepository: AccountRepository) : BaseViewModel() {
    val accounts: MutableStateFlow<ArrayList<AccountModel>?> = MutableStateFlow(null)

    fun getAccount(
        fullname: String? = null,
        email: String? = null,
        roleId: Int? = null
    ) = viewModelScope.launch {
        loading.postValue(true)
        accountRepository.getAccount(fullname, email, roleId).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                accounts.value = response.body()?.data
            } else {
                errorApiLiveData.postValue("Lỗi Server")
            }
        }
    }

    fun addAccount(addAccountRequest: AddAccountRequest) = viewModelScope.launch {
        loading.postValue(true)
        accountRepository.addAccount(addAccountRequest).let { response ->
            loading.postValue(false)
            if(response.isSuccessful) {
                if(response.body() != null) {
                    getAccount()
                }
            } else {
                errorApiLiveData.postValue("Lỗi server")
            }
        }
    }
}