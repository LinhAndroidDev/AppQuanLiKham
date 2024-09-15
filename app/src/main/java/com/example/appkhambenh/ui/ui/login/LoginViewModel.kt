package com.example.appkhambenh.ui.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.base.doOnFailure
import com.example.appkhambenh.ui.data.remote.base.doOnLoading
import com.example.appkhambenh.ui.data.remote.base.doOnSuccess
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.LoginDoctorRepository
import com.example.appkhambenh.ui.data.remote.repository.user.LoginRepository
import com.example.appkhambenh.ui.utils.SharePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val loginDoctorRepository: LoginDoctorRepository,
    private val shared: SharePreferenceRepository,
) :
    BaseViewModel() {
    val loginSuccessLiveData = MutableLiveData<Boolean>()
    private val _doctorLoginSuccess = MutableStateFlow(false)
    val doctorLoginSuccess = _doctorLoginSuccess.asStateFlow()

    suspend fun requestLoginUser(loginModel: LoginModel) {
        loading.postValue(true)
        try {
            loginRepository.loginUser(loginModel).let { response ->
                loading.postValue(false)
                if (response.isSuccessful) {
                    response.body().let {
                        when (it?.statusCode) {
                            ApiClient.STATUS_CODE_SUCCESS -> {
                                loginSuccessLiveData.postValue(true)
                                it.data?.token?.let { token ->
                                    shared.saveAuthorization(token)
                                }
                            }

                            else -> {
                                errorApiLiveData.postValue(it?.message)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }

    fun loginDoctor(email: String, password: String) = viewModelScope.launch {
        loginDoctorRepository.loginDoctor(email, password)
            .doOnLoading {
                loading.postValue(it)
            }
            .doOnFailure {
                errorApiLiveData.postValue(it?.message ?: "Unknown error")
            }
            .doOnSuccess {
                shared.saveAuthorization(it.auth?.accessToken.toString())
                shared.saveRollUser(it.user?.roleId ?: 1)
                _doctorLoginSuccess.value = true
            }.collect()
    }
}