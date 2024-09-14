package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.AccountModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.AccountRepository
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAccountViewModel @Inject constructor(private val accountRepository: AccountRepository) : BaseViewModel() {
    private val _accounts: MutableStateFlow<ArrayList<AccountModel>?> = MutableStateFlow(null)
    val accounts = _accounts.asStateFlow()

    fun getAccount(
        fullname: String? = null,
        email: String? = null,
        roleId: Int? = null
    ) = viewModelScope.launch {
        loading.postValue(true)
        accountRepository.getAccount(fullname, email, roleId)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }.collect {
                loading.postValue(false)
                _accounts.value = it.data
            }
    }

    fun addAccount(addAccountRequest: AddAccountRequest) = viewModelScope.launch {
        loading.postValue(true)
        accountRepository.addAccount(addAccountRequest)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                getAccount()
            }
    }
}