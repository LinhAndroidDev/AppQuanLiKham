package com.example.appkhambenh.ui.ui.user.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.UserModel
import com.example.appkhambenh.ui.data.remote.repository.HomeRepository
import com.example.appkhambenh.ui.utils.DateUtils
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    @ApplicationContext private val context: Context,
) :
    BaseViewModel() {
    var isSuccessful = MutableLiveData<Boolean>()
    var avatar = MutableStateFlow<InputStream?>(null)

    private fun saveInfo(
        user: UserModel,
    ) {
        with(SharePreferenceRepositoryImpl(context)) {
            user.apply {
                saveUserId(this._id)
                name?.let { saveUserName(it) }
                email?.let { saveUserEmail(it) }
                birthday?.let { saveUserBirth(DateUtils.convertLongToDate(it)) }
                phoneNumber?.let { saveUserPhone(it) }
                address?.let { saveUserAddress(it) }
                identification?.let { saveIdentification(it) }
                gender?.let { saveUserSex(it) }
                avatar?.let { saveUserAvatar(it) }
            }
        }
    }

    fun getUserInfo() = viewModelScope.launch {
        loading.postValue(true)
        try {
            repository.getUserInfo()
                .let { response ->
                    loading.postValue(false)
                    if (response.isSuccessful) {
                        response.body().let {
                            when (it?.statusCode) {
                                ApiClient.STATUS_CODE_SUCCESS -> {
                                    isSuccessful.postValue(true)
                                    it.data?.let { user ->
                                        saveInfo(user)
                                    }
                                }

                                else -> {
                                    errorApiLiveData.postValue(it?.message)
                                }
                            }
                        }
                    } else {
//                        errorApiLiveData.postValue(response.body()?.message)
                    }
                }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }

    fun getAvatarUser(userId: Int) = viewModelScope.launch {
        try {
            repository.getAvatar(userId).let {
                if (it.isSuccessful) {
                    avatar.value = it.body()?.byteStream()
                } else {
                    errorApiLiveData.postValue("${it.body()}")
                }
            }
        } catch (e: Exception) {
            errorApiLiveData.postValue(e.message)
        }
    }
}