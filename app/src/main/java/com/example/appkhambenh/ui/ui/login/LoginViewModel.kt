package com.example.appkhambenh.ui.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.utils.PreferenceKey
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : BaseViewModel() {
    val loginSuccessLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private fun saveInfo(
        name: String,
        birth: String,
        avatar: String,
        phone: String,
        email: String
    ) {
        mPreferenceUtil.defaultPref().edit()
            .putString(PreferenceKey.USER_NAME, name)
            .apply()
        mPreferenceUtil.defaultPref().edit()
            .putString(PreferenceKey.USER_BIRTH, birth)
            .apply()
        mPreferenceUtil.defaultPref().edit()
            .putString(PreferenceKey.USER_AVATAR, avatar)
            .apply()
        mPreferenceUtil.defaultPref().edit()
            .putString(PreferenceKey.USER_PHONE, phone)
            .apply()
        mPreferenceUtil.defaultPref().edit()
            .putString(PreferenceKey.USER_EMAIL, email)
            .apply()
    }

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
                            saveInfo(
                                response.body()?.result?.name.toString(),
                                response.body()?.result?.birth.toString(),
                                response.body()?.result?.avatar.toString(),
                                response.body()?.result?.phone.toString(),
                                response.body()?.result?.email.toString()
                            )
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