package com.example.appkhambenh.ui.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.utils.PreferenceKey
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : BaseViewModel() {
    val loginSuccessLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun requestLoginUser(requestBodyEmail: RequestBody, requestBodyPassword: RequestBody) {
        loadingLiveData.value = true
        ApiClient.shared().loginUser(requestBodyEmail, requestBodyPassword)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    loadingLiveData.value = false
                    response.body()?.let {
                        if (it.statusCode == ApiClient.STATUS_CODE_SUCCESS) {
                            loginSuccessLiveData.value = true
                            mPreferenceUtil.defaultPref().edit()
                                .putInt(PreferenceKey.USER_ID, it.result?.id!!)
                                .apply()
                        }else{
                            errorApiLiveData.value = it.message
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loadingLiveData.value = false
                    errorApiLiveData.value = t.message
                }

            })
    }
}