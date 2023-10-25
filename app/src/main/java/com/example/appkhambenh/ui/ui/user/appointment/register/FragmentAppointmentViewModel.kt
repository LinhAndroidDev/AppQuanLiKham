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
    var isChangeStatusLiveData = MutableLiveData<Boolean>()
    var loadingEditLiveData = MutableLiveData<Boolean>()
    var editAppointSuccessfulLiveData = MutableLiveData<Boolean>()

    fun addAppoint(
        service: RequestBody,
        department: RequestBody,
        doctor: RequestBody,
        date: RequestBody,
        hour: RequestBody,
        reasons: RequestBody,
        id_user: RequestBody
    ){
        isLoadingLiveData.postValue(true)
        ApiClient.shared().addAppointment(service, department, doctor, date, hour, reasons, id_user)
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

    fun changeStatusWorkingTime(
        id_day: RequestBody,
        hour: RequestBody,
        is_registered: RequestBody
    ){
        ApiClient.shared().changeStatusWorkingTime(id_day, hour, is_registered)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ChangeStatusWorkingTimeResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: ChangeStatusWorkingTimeResponse) {
                    when(t.statusCode) {
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            isChangeStatusLiveData.postValue(true)
                        }
                        else -> {
                            isChangeStatusLiveData.postValue(false)
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }

    fun editAppoint(
        service: RequestBody,
        department: RequestBody,
        doctor: RequestBody,
        date: RequestBody,
        hour: RequestBody,
        reasons: RequestBody,
        id_user: RequestBody
    ){
        loadingEditLiveData.postValue(true)
        ApiClient.shared().editAppoint(service, department, doctor, date, hour, reasons, id_user)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RegisterAppointmentResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    loadingEditLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: RegisterAppointmentResponse) {
                    loadingEditLiveData.postValue(false)
                    when(t.statusCode){
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            editAppointSuccessfulLiveData.postValue(true)
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}