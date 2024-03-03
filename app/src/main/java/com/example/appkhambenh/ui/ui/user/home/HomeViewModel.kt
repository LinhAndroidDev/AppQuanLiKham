package com.example.appkhambenh.ui.ui.user.home

import android.content.Context
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.UserModel
import com.example.appkhambenh.ui.data.remote.repository.GetInfoRepository
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import com.example.appkhambenh.ui.utils.convertIntToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getInfoRepository: GetInfoRepository) :
    BaseViewModel() {

    private fun saveInfo(
        context: Context,
        user: UserModel,
    ) {
        SharePreferenceRepositoryImpl(context).saveUserId(user._id)
        SharePreferenceRepositoryImpl(context).saveUserName(user.name)
        SharePreferenceRepositoryImpl(context).saveUserEmail(user.email)
        if (user.birthday != null) {
            SharePreferenceRepositoryImpl(context).saveUserBirth(convertIntToDate(user.birthday))
        }
        user.gender.let {
            SharePreferenceRepositoryImpl(context).saveUserSex(it ?: 0)
        }
        SharePreferenceRepositoryImpl(context).saveUserAvatar("")
    }

    suspend fun getUserInfo(context: Context) {
        loading.postValue(true)
        val token = SharePreferenceRepositoryImpl(context).getAuthorization()
        try {
            getInfoRepository.getUserInfo("Bearer $token")
                .let { response ->
                    loading.postValue(false)
                    if (response.isSuccessful) {
                        response.body().let {
                            when (it?.statusCode) {
                                ApiClient.STATUS_CODE_SUCCESS -> {
                                    saveInfo(context, it.data)
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