package com.example.appkhambenh.ui.ui.user.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : BaseViewModel() {
    var userLiveData = MutableLiveData<UserInfoResponse>()

    private fun saveInfo(
        context: Context,
        infoResponse: UserInfoResponse
    ) {
        SharePreferenceRepositoryImpl(context).saveUserId(infoResponse.data!!._id)
        SharePreferenceRepositoryImpl(context).saveUserName(infoResponse.data.name.toString())
        SharePreferenceRepositoryImpl(context).saveUserEmail(infoResponse.data.mail.toString())
        SharePreferenceRepositoryImpl(context).saveUserType(infoResponse.data.type_account!!)
    }

    fun getUserInfo(token: String, context: Context){
        loading.postValue(true)
        ApiClient.shared().getUserInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<UserInfoResponse>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    loading.postValue(false)
                    errorApiLiveData.postValue(e.message)
                }

                override fun onComplete() {

                }

                override fun onNext(t: UserInfoResponse) {
                    loading.postValue(false)
                    when(t.statusCode) {
                        ApiClient.STATUS_CODE_SUCCESS -> {
                            userLiveData.postValue(t)
                            saveInfo(context, t)
                        }
                        else -> {
                            errorApiLiveData.postValue(t.message)
                        }
                    }
                }

            })
    }
}