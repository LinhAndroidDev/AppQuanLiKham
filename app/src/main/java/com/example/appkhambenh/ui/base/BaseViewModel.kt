package com.example.appkhambenh.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorApiLiveData = MutableLiveData<String>()
}