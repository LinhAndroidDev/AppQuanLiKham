package com.example.appkhambenh.ui.ui.doctor.time_working

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.WorkingDate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class EditTimeWorkingViewModel : BaseViewModel() {
    var isSuccessfulLiveData = MutableLiveData<Boolean>()
    var isLoadingUpdateLiveData = MutableLiveData<Boolean>()
    var isLoadingGetTimeLiveData = MutableLiveData<Boolean>()
    var workingDateLiveData = MutableLiveData<WorkingDate>()
    var deleteSuccessful: Boolean = false
    var editSuccessful: Boolean = false

    fun getListWorkingTime(day: RequestBody, id_doctor: RequestBody) {
        isLoadingGetTimeLiveData.postValue(true)
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

    fun updateWorkingTime(day: RequestBody, hour: RequestBody, id_doctor: RequestBody) {
        isLoadingUpdateLiveData.value = true
        ApiClient.shared().updateWorkingTime(day, hour, id_doctor)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UpdateTimeResponse> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    isLoadingUpdateLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {}

                override fun onNext(t: UpdateTimeResponse) {
                    isLoadingUpdateLiveData.postValue(false)
                    when(t.statusCode) {
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            isSuccessfulLiveData.postValue(true)
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }

    fun deleteWorkingTime(id_day: RequestBody, hour: RequestBody){
        ApiClient.shared().deleteWorkingTime(id_day, hour)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UpdateTimeResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: UpdateTimeResponse) {
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

    fun editWorkingTime(
        id_day: RequestBody,
        hour: RequestBody,
        new_hour: RequestBody
    ){
        ApiClient.shared().editWorkingTime(id_day, hour, new_hour)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UpdateTimeResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: UpdateTimeResponse) {
                    when(t.statusCode) {
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            editSuccessful = true
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}