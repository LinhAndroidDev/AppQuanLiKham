package com.example.appkhambenh.ui.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.LoginDoctorRepository
import com.example.appkhambenh.ui.data.remote.repository.user.LoginRepository
import com.example.appkhambenh.ui.utils.SharePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
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
    val doctorLoginSuccess = MutableStateFlow(false)

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
        loading.postValue(true)
        loginDoctorRepository.loginDoctor(email, password)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
            .collect {
                loading.postValue(false)
                shared.saveAuthorization(it.auth?.accessToken.toString())
                shared.saveRollUser(it.user?.roleId ?: 1)
                doctorLoginSuccess.value = true
            }
    }
}