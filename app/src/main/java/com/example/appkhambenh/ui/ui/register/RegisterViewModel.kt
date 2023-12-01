package com.example.appkhambenh.ui.ui.register

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : BaseViewModel() {
    val loadingLiveData = MutableLiveData<Boolean>()
    val registerSuccessful = MutableLiveData<Boolean>()

    fun requestRegisterUser(
        email: RequestBody,
        sex: RequestBody,
        specialist: RequestBody,
        password: RequestBody,
        name: RequestBody,
        birth: RequestBody,
        phone: RequestBody,
        address: RequestBody,
        type: RequestBody,
    ) {

        loadingLiveData.value = true

        ApiClient.shared()
            .registerUser(email, sex, specialist, password, name, birth, phone, address, type)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>,
                ) {
                    loadingLiveData.value = false
                    if (response.body()?.statusCode == ApiClient.STATUS_USER_EXIST) {
                        errorApiLiveData.value = response.body()?.message
                    }
                    if (response.body()?.statusCode == ApiClient.STATUS_CODE_SUCCESS) {
                        registerSuccessful.value = true
                        errorApiLiveData.value = response.body()?.message
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    loadingLiveData.value = false
                    errorApiLiveData.value = t.message
                }

            })
    }
}