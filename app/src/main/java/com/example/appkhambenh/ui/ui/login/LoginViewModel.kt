package com.example.appkhambenh.ui.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.data.remote.repository.doctor.LoginDoctorRepository
import com.example.appkhambenh.ui.data.remote.repository.user.LoginRepository
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val loginDoctorRepository: LoginDoctorRepository,
    @ApplicationContext private val context: Context
) :
    BaseViewModel() {
    val loginSuccessLiveData = MutableLiveData<Boolean>()
    val doctorLoginSuccess = MutableStateFlow(false)

    suspend fun requestLoginUser(context: Context, loginModel: LoginModel) {
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
                                    SharePreferenceRepositoryImpl(context).saveAuthorization(token)
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

    suspend fun loginDoctor(email: String, password: String) {
        loading.postValue(true)
        try {
            loginDoctorRepository.loginDoctor(email, password).let { response ->
                loading.postValue(false)
                if(response.isSuccessful) {
                    if (response.body()?.error == null) {
                        doctorLoginSuccess.value = true
                        SharePreferenceRepositoryImpl(context).saveAuthorization(response.body()?.auth?.accessToken.toString())
                    } else {
                        errorApiLiveData.postValue(response.body()?.error)
                    }
                } else {
                    errorApiLiveData.postValue(response.errorBody()?.string())
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}