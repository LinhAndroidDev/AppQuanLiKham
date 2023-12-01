package com.example.appkhambenh.ui.ui.user.navigation.notification

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.RegisterChecking
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class NotificationViewModel : BaseViewModel() {
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val listAppointmentLiveData = MutableLiveData<ArrayList<RegisterChecking>>()

    fun getListNotification(id_user: RequestBody){
        isLoadingLiveData.postValue(true)
        ApiClient.shared().getListAppointment(id_user)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RegisterChecking>> {
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

                    try {
                        listAppointmentLiveData.postValue(t.filter {
                            it.checkAppointExpired()
                        } as ArrayList<RegisterChecking>)
                    }catch (e: Exception){
                        errorApiLiveData.postValue(e.message)
                    }
                }

            })
    }
}