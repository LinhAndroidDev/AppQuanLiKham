package com.example.appkhambenh.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appkhambenh.ui.utils.PreferenceUtil

open class BaseViewModel : ViewModel() {
    lateinit var mPreferenceUtil: PreferenceUtil
    val errorApiLiveData = MutableLiveData<String>()
}