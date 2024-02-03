package com.example.appkhambenh.ui.ui.user.manage_appointment

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.model.RegisterChecking

class ManageAppointmentViewModel : BaseViewModel() {
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var listAppointmentLiveData = MutableLiveData<ArrayList<RegisterChecking>>()
    var deleteSuccessful: Boolean = false
}