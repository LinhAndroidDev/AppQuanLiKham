package com.example.appkhambenh.ui.data.remote

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val STATUS_CODE_SUCCESS = 200
    const val STATUS_USER_EXIST = 400
    const val STATUS_USER_NOT_EXIT = 401
    const val STATUS_SERVER_NOT_RESPONSE = 500

    private const val BASE_URL = "http://192.168.1.8/quanlikham/"
    private var RETROFIT: Retrofit? = null
    private var API_SERVICE: ApiService? = null

    private fun getClient(): Retrofit? {
        return RETROFIT?: synchronized(this){
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
            RETROFIT = retrofit
            RETROFIT
        }
    }

    fun shared(): ApiService {
        return API_SERVICE?: synchronized(this){
            val apiService = getClient()?.create(ApiService::class.java)
            API_SERVICE = apiService
            API_SERVICE!!
        }
    }

}