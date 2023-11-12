package com.example.appkhambenh.ui.ui.user.appointment.time

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.WorkingDate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class TimeWorkingViewModel: BaseViewModel() {
    var isLoadingGetTimeLiveData = MutableLiveData<Boolean>()
    var workingDateLiveData = MutableLiveData<WorkingDate>()

    fun getListWorkingTime(day: RequestBody, id_doctor: RequestBody) {
        isLoadingGetTimeLiveData.value = true
        ApiClient.shared().getWorkingTime(day, id_doctor)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WorkingDate> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    isLoadingGetTimeLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {}

                override fun onNext(t: WorkingDate) {
                    isLoadingGetTimeLiveData.postValue(false)
                    workingDateLiveData.postValue(t)
                }

            })
    }
}