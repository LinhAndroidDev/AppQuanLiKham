package com.example.appkhambenh.ui.ui.user.medicine

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.Medicine
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.*

class MedicineViewModel : BaseViewModel() {
    val listMedicineLiveData = MutableLiveData<List<Medicine>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getDataMedicine(){
        loadingLiveData.value = true
        ApiClient.shared().getListMedicine()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Medicine>>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    loadingLiveData.value = false
                    errorApiLiveData.value = e.message
                }

                override fun onComplete() {

                }

                override fun onNext(t: List<Medicine>) {
                    loadingLiveData.value = false
                    listMedicineLiveData.value = t
                }

            })
//        ApiClient.shared().getListMedicine()
//            .enqueue(object : Callback<List<Medicine>>{
//                override fun onResponse(
//                    call: Call<List<Medicine>>,
//                    response: Response<List<Medicine>>,
//                ) {
//                    loadingLiveData.value = false
//                    listMedicineLiveData.value = response.body()
//                }
//
//                override fun onFailure(call: Call<List<Medicine>>, t: Throwable) {
//                    loadingLiveData.value = false
//                    errorApiLiveData.value = t.message
//                }
//
//            })
    }
}