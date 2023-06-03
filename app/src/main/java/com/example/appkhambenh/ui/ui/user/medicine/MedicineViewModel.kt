package com.example.appkhambenh.ui.ui.user.medicine

import androidx.lifecycle.MutableLiveData
import com.example.appkhambenh.ui.base.BaseViewModel
import com.example.appkhambenh.ui.data.remote.ApiClient
import com.example.appkhambenh.ui.model.Medicine
import retrofit2.*

class MedicineViewModel : BaseViewModel() {
    val listMedicineLiveData = MutableLiveData<List<Medicine>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getDataMedicine(){
        ApiClient.shared().getListMedicine()
            .enqueue(object : Callback<List<Medicine>>{
                override fun onResponse(
                    call: Call<List<Medicine>>,
                    response: Response<List<Medicine>>,
                ) {
                    loadingLiveData.value = false
                    listMedicineLiveData.value = response.body()
                }

                override fun onFailure(call: Call<List<Medicine>>, t: Throwable) {
                    loadingLiveData.value = false
                    errorApiLiveData.value = t.message
                }

            })

//        loadingLiveData.value = true
//        GlobalScope.launch(Dispatchers.IO){
//            val response = ApiClient.shared().getListMedicine()
//            if(response.isSuccessful){
//                loadingLiveData.value = false
//                listMedicineLiveData.value = response.body()
//            }else{
//                loadingLiveData.value = false
//                errorApiLiveData.value = response.message()
//            }
//        }
    }
}