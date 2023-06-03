package com.example.appkhambenh.ui.ui.user.appointment

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.WorkingDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkingTimeViewModel: BaseViewModel() {
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var listWorkingTimeLiveData = MutableLiveData<ArrayList<WorkingDate>>()

    fun getWorkingDate(){
        isLoadingLiveData.value = true
        ApiClient.shared().getWorkingDate()
            .enqueue(object : Callback<ArrayList<WorkingDate>>{
                override fun onResponse(
                    call: Call<ArrayList<WorkingDate>>,
                    response: Response<ArrayList<WorkingDate>>,
                ) {
                    isLoadingLiveData.value = false
                    if(response.isSuccessful){
                        response.body().let {
                            listWorkingTimeLiveData.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<WorkingDate>>, t: Throwable) {
                    isLoadingLiveData.value = false
                    errorApiLiveData.value = t.message
                }

            })
    }
}