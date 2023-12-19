package com.example.appkhambenh.ui.ui.user.navigation.setting.address

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.Province
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddressViewModel : BaseViewModel() {
    val provinces = MutableLiveData<ArrayList<Province>>()

    fun getProvince(){
        ApiClient.sharedProvince().getProvinces("3", "IwAR34BV5hlL5AQno3_yiJVQQaU7qSibsHN8o7HQWaBzlapjrlnf0xYhcoReg")
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<Province>>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: ArrayList<Province>) {
                    provinces.postValue(t)
                }

            })
    }
}