package com.example.appkhambenh.ui.ui.doctor.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.model.AccountModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.AccountRepository
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentAccountViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    BaseViewModel() {
    private val _accounts: MutableStateFlow<ArrayList<AccountModel>?> = MutableStateFlow(null)
    val accounts = _accounts.asStateFlow()

    fun getAccount(
        fullname: String? = null,
        email: String? = null,
        roleId: Int? = null,
    ) = viewModelScope.launch {
        accountRepository.getAccount(fullname, email, roleId)
            .handleCallApi()
            .doOnSuccess {
                _accounts.value = it.data
            }.collect()
    }

    fun addAccount(addAccountRequest: AddAccountRequest) = viewModelScope.launch {
        accountRepository.addAccount(addAccountRequest)
            .handleCallApi()
            .doOnSuccess { getAccount() }
            .collect()
    }
}