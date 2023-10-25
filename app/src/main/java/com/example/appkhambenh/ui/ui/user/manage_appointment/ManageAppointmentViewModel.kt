package com.example.appkhambenh.ui.ui.user.manage_appointment

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.ui.user.appointment.register.RegisterAppointmentResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class ManageAppointmentViewModel : BaseViewModel() {
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var listAppointmentLiveData = MutableLiveData<ArrayList<RegisterChecking>>()
    var deleteSuccessful: Boolean = false

    fun getListAppointment(id_user: RequestBody){
        isLoadingLiveData.postValue(true)
        ApiClient.shared().getListAppointment(id_user)
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

                    listAppointmentLiveData.postValue(t.filter {
                        !it.checkAppointExpired()
                    } as ArrayList<RegisterChecking>)
                }

            })
    }

    fun deleteAppoint(
        is_cancel: RequestBody,
        day: RequestBody,
        hour: RequestBody,
        is_registered: RequestBody
    ) {
        ApiClient.shared().deleteAppoint(is_cancel, day, hour, is_registered)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RegisterAppointmentResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: RegisterAppointmentResponse) {
                    when(t.statusCode){
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            deleteSuccessful = true
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}