package com.example.appkhambenh.ui.ui.user.manage_appointment

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.RegisterChecking
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ManageAppointmentViewModel : BaseViewModel() {
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var listAppointmentLiveData = MutableLiveData<ArrayList<RegisterChecking>>()

    fun getListAppointment(){
        isLoadingLiveData.postValue(true)
        ApiClient.shared().getListAppointment()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RegisterChecking>>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isLoadingLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: List<RegisterChecking>) {
                    isLoadingLiveData.postValue(false)

                    listAppointmentLiveData.postValue(t as ArrayList<RegisterChecking>)
                }

            })
    }
}