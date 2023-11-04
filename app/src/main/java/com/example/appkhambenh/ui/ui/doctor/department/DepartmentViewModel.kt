package com.example.appkhambenh.ui.ui.doctor.department

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class DepartmentViewModel : BaseViewModel() {
    var addSuccessfulLiveData = MutableLiveData<Boolean>()

    fun addDepartment(name: RequestBody){
        ApiClient.shared().addDepartment(name)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DepartmentResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: DepartmentResponse) {
                    when(t.statusCode){
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            addSuccessfulLiveData.postValue(true)
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}