package com.example.appkhambenh.ui.ui.user.navigation.password

import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.model.ChangePasswordModel
import com.example.appkhambenh.ui.data.remote.repository.ChangePasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val repository: ChangePasswordRepository) :
    BaseViewModel() {
        val isSuccessful = MutableStateFlow(false)

        suspend fun changePassword(userId: Int, changePasswordModel: ChangePasswordModel) {
            loading.postValue(true)
            repository.changePassword(userId, changePasswordModel).let {
                try {
                    loading.postValue(false)
                    if(it.isSuccessful) {
                        errorApiLiveData.postValue(it.body()?.message)
                        when(it.body()?.statusCode) {
                            ApiClient.STATUS_CODE_SUCCESS -> {
                                isSuccessful.value = true
                            }
                        }
                    }
                } catch (e: Exception) {
                    loading.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }
            }
        }
}