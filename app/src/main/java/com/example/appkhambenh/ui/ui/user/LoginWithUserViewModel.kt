package com.example.appkhambenh.ui.ui.user

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.utils.PreferenceKey
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginWithUserViewModel : BaseViewModel() {
    var loadingLiveData = MutableLiveData<Boolean>()
    var nameLiveData = MutableLiveData<String>()
    var birthLiveData = MutableLiveData<String>()
    var avatarLiveData = MutableLiveData<String>()
    var phoneLiveData = MutableLiveData<String>()
    var emailLiveData = MutableLiveData<String>()

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

    fun getUserInfo(user_id: RequestBody){
        loadingLiveData.value = true
        ApiClient.shared().getUserInfo(user_id)
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
                                    nameLiveData.value = it.result?.name.toString()
                                    birthLiveData.value = it.result?.birth.toString()
                                    avatarLiveData.value = it.result?.avatar.toString()
                                    phoneLiveData.value = it.result?.phone.toString()
                                    emailLiveData.value = it.result?.email.toString()
                                    saveInfo(
                                        response.body()?.result?.name.toString(),
                                        response.body()?.result?.birth.toString(),
                                        response.body()?.result?.avatar.toString(),
                                        response.body()?.result?.phone.toString(),
                                        response.body()?.result?.email.toString()
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