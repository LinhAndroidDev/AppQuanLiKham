package com.example.appkhambenh.ui.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel : BaseViewModel() {
    val loginSuccessLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun requestLoginUser(loginModel: LoginModel, context: Context) {
        loadingLiveData.postValue(true)
        ApiClient.sharedFromWeb().loginUser(loginModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<LoginResponse> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    loadingLiveData.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {}

                override fun onNext(t: LoginResponse) {
                    loadingLiveData.postValue(false)
                    when (t.statusCode) {
                        null -> {
                            loginSuccessLiveData.postValue(true)
                            SharePreferenceRepositoryImpl(context).saveAuthorization(t.data?.token.toString())
                        }

                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })

    }
}