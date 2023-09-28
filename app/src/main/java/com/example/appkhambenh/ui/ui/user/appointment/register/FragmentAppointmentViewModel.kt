package com.example.appkhambenh.ui.ui.user.appointment.register

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class FragmentAppointmentViewModel : BaseViewModel() {
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var isSuccessfulLiveData = MutableLiveData<Boolean>()

    fun addAppoint(
        service: RequestBody,
        department: RequestBody,
        doctor: RequestBody,
        date: RequestBody,
        hour: RequestBody,
        reasons: RequestBody
    ){
        isLoadingLiveData.postValue(true)
        ApiClient.shared().addAppointment(service, department, doctor, date, hour, reasons)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RegisterAppointmentResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isLoadingLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: RegisterAppointmentResponse) {
                    isLoadingLiveData.postValue(false)
                    isSuccessfulLiveData.postValue(true)
                }

            })
    }
}