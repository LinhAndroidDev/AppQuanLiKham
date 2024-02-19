package com.example.appkhambenh.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl

open class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorApiLiveData = MutableLiveData<String>()
}