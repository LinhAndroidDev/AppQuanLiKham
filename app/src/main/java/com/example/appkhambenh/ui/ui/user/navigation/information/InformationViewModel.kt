package com.example.appkhambenh.ui.ui.user.navigation.information

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody

class InformationViewModel : BaseViewModel() {
    var updateInfoSuccessfulLiveData = MutableLiveData<Boolean>()
    var isLoadingLiveData = MutableLiveData<Boolean>()

    fun updateInfo(
        id: RequestBody,
        name: RequestBody,
        email: RequestBody,
        birth: RequestBody,
        phone: RequestBody,
        address: RequestBody,
    ){
        isLoadingLiveData.postValue(true)
        ApiClient.shared().updateInfo(id, name, email, birth, phone, address)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UserInfoResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isLoadingLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: UserInfoResponse) {
                    isLoadingLiveData.postValue(false)
                    when(t.statusCode){
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            updateInfoSuccessfulLiveData.postValue(true)
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}