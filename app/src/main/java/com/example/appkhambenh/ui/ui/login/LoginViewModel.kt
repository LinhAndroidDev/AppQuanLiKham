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

    suspend fun requestLoginUser(loginModel: LoginModel, context: Context) {
        loading.postValue(true)
        try {
            loginRepository.loginUser(loginModel).let { response ->
                if (response.isSuccessful) {
                    loading.postValue(false)
                    response.body().let {
                        when (it?.statusCode) {
                            ApiClient.STATUS_CODE_SUCCESS -> {
                                loginSuccessLiveData.postValue(true)
                                SharePreferenceRepositoryImpl(context).saveAuthorization(it.data?.token.toString())
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