package com.example.appkhambenh.ui.ui.user.navigation.setting.address

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.model.Address
import com.example.appkhambenh.ui.utils.getDataDistrict
import com.example.appkhambenh.ui.utils.getDataProvince
import com.example.appkhambenh.ui.utils.getDataWard
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddressViewModel : BaseViewModel() {
    var provinces = arrayListOf<Address>()
    var districts = arrayListOf<Address>()
    var wards = arrayListOf<Address>()
    var successful = MutableLiveData<Boolean>()

    fun getDataAddress(context: Activity) = viewModelScope.launch {
        try {
            async {
                provinces = ArrayList(getDataProvince(context).values)
            }.await()

            async {
                districts = ArrayList(getDataDistrict(context).values)
            }.await()

            async {
                wards = ArrayList(getDataWard(context).values)
            }.await()

            successful.postValue(true)
        } catch (e: Exception) {
            errorApiLiveData.postValue(e.message)
        }
    }
}