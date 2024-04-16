package com.example.appkhambenh.ui.ui.user.navigation.setting

import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.model.ProfileModel
import com.example.appkhambenh.ui.data.remote.model.UserModel
import com.example.appkhambenh.ui.data.remote.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateInformationViewModel @Inject constructor(private val repository: ProfileRepository) :
    BaseViewModel() {
    val infoUser = MutableStateFlow<UserModel?>(null)
    val successful = MutableStateFlow(false)

    suspend fun getUserInfo() {
        loading.postValue(true)
        repository.getUserInfo().let {
            try {
                loading.postValue(false)
                if (it.isSuccessful) {
                    infoUser.value = it.body()?.data
                }
            } catch (e: Exception) {
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
        }
    }

    fun updateProfile(profileModel: ProfileModel, userId: Int) = viewModelScope.launch {
        loading.postValue(true)
        repository.updateProfile(profileModel, userId).let {
            try {
                loading.postValue(false)
                if (it.isSuccessful) {
                    successful.value = true
                    errorApiLiveData.postValue(it.body()?.message)
                } else {
                    errorApiLiveData.postValue(it.body()?.message)
                }
            } catch (e: Exception) {
                loading.postValue(false)
                errorApiLiveData.postValue(e.message)
            }
        }
    }
}