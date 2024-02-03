package com.example.appkhambenh.ui.ui.register

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.RegisterResponse
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterViewModel : BaseViewModel() {
    val loadingLiveData = MutableLiveData<Boolean>()
    val registerSuccessful = MutableLiveData<Boolean>()

    fun requestRegisterUser(
        registerModel: RegisterModel,
    ) {

        loadingLiveData.postValue(true)

        ApiClient.sharedFromWeb()
            .registerUser(registerModel)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.rxjava3.core.Observer<RegisterResponse> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    loadingLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {}

                override fun onNext(t: RegisterResponse) {
                    loadingLiveData.postValue(false)
                    when (t.statusCode) {
                        200 -> {
                            registerSuccessful.value = true
                            errorApiLiveData.postValue(t.message)
                        }

                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}