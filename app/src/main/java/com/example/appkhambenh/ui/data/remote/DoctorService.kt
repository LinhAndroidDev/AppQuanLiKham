package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientModel
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface DoctorService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginDoctor(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<DoctorLoginResponse>

    @GET("patients")
    suspend fun getListPatient(): Response<PatientResponse>
}