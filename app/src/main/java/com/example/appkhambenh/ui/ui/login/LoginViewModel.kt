package com.example.appkhambenh.ui.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.data.remote.repository.LoginRepository
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {
    val loginSuccessLiveData = MutableLiveData<Boolean>()

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
}