package com.example.appkhambenh.ui.ui.user.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : BaseViewModel() {
    var loadingLiveData = MutableLiveData<Boolean>()
    var userLiveData = MutableLiveData<UserInfoResponse>()

    private fun saveInfo(
        context: Context,
        name: String,
        birth: String,
        avatar: String,
        phone: String,
        email: String,
        sex: Int,
        address: String,
        type: Int,
    ) {
        SharePreferenceRepositoryImpl(context).saveUserName(name)
        SharePreferenceRepositoryImpl(context).saveUserBirth(birth)
        SharePreferenceRepositoryImpl(context).saveUserAvatar(avatar)
        SharePreferenceRepositoryImpl(context).saveUserPhone(phone)
        SharePreferenceRepositoryImpl(context).saveUserEmail(email)
        SharePreferenceRepositoryImpl(context).saveUserSex(sex)
        SharePreferenceRepositoryImpl(context).saveUserAddress(address)
        SharePreferenceRepositoryImpl(context).saveUserType(type)
    }

    fun getUserInfo(userId: RequestBody, context: Context){
        loadingLiveData.value = true
        ApiClient.shared().getUserInfo(userId)
            .enqueue(object : Callback<UserInfoResponse>{
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>,
                ) {
                    loadingLiveData.value = false
                    if(response.isSuccessful){
                        response.body().let {
                            when(it?.statusCode){
                                ApiClient.STATUS_CODE_SUCCESS->{
                                    userLiveData.value = it
                                    saveInfo(
                                        context,
                                        response.body()?.result?.name.toString(),
                                        response.body()?.result?.birth.toString(),
                                        response.body()?.result?.avatar.toString(),
                                        response.body()?.result?.phone.toString(),
                                        response.body()?.result?.email.toString(),
                                        response.body()?.result?.sex ?: -1,
                                        response.body()?.result?.address.toString(),
                                        response.body()?.result?.type ?: -1
                                    )
                                }
                                ApiClient.STATUS_USER_EXIST->{
                                    errorApiLiveData.value = it.message
                                }
                                ApiClient.STATUS_USER_NOT_EXIT->{
                                    errorApiLiveData.value = it.message
                                }
                                ApiClient.STATUS_SERVER_NOT_RESPONSE->{
                                    errorApiLiveData.value = it.message
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    loadingLiveData.value = false
                    errorApiLiveData.value = t.message
                }

            })
    }
}