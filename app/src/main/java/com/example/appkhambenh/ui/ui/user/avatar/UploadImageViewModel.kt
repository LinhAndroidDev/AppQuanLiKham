package com.example.appkhambenh.ui.ui.user.avatar

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.repository.UpdateAvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadImageViewModel @Inject constructor(private val repository: UpdateAvatarRepository) :
    BaseViewModel() {
    val isSuccessful = MutableStateFlow(false)

    suspend fun updateAvatar(id: Int, avatar: MultipartBody.Part) {
        loading.postValue(true)
        try {
            repository.updateAvatar(id, avatar).let {
                loading.postValue(false)
                if (it.isSuccessful) {
                    when(it.body()?.statusCode) {
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            errorApiLiveData.postValue(it.body()?.message)
                            isSuccessful.value = true
                        }

                        else -> {
                            errorApiLiveData.postValue(it.body()?.message)
                        }
                    }
                } else {
                    errorApiLiveData.postValue(it.body()?.message)
                }
            }
        } catch (e: Exception) {
            loading.postValue(false)
            errorApiLiveData.postValue(e.message)
        }
    }
}