package com.example.appkhambenh.ui.ui.user.avatar

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.UploadImageResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadImageViewModel : BaseViewModel() {
    val loadingLiveData = MutableLiveData<Boolean>()
    val isSuccessfulLiveData = MutableLiveData<Boolean>()

    fun uploadImage(
        userId: RequestBody,
        avatar: RequestBody
    ){
        loadingLiveData.value = true
        ApiClient.shared().uploadImage(userId, avatar)
            .enqueue(object : Callback<UploadImageResponse>{
                override fun onResponse(
                    call: Call<UploadImageResponse>,
                    response: Response<UploadImageResponse>,
                ) {
                    loadingLiveData.value = false
                    if(response.isSuccessful){
                        response.body().let {
                            when(it?.statusCode){
                                ApiClient.STATUS_CODE_SUCCESS->{
                                    isSuccessfulLiveData.value = true
                                }
                                ApiClient.STATUS_USER_NOT_EXIT->{
                                    errorApiLiveData.value = it.message
                                }
                                ApiClient.STATUS_USER_EXIST->{
                                    errorApiLiveData.value = it.message
                                }
                                ApiClient.STATUS_SERVER_NOT_RESPONSE->{
                                    errorApiLiveData.value = it.message
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                    loadingLiveData.value = false
                    errorApiLiveData.value = t.message
                }

            })
    }
}