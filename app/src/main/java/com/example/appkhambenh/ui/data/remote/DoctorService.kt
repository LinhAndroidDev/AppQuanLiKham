package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoPatientResponse
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DoctorService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginDoctor(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<DoctorLoginResponse>

    @GET("patients")
    suspend fun getListPatient(): Response<PatientResponse>

    @PUT("patients/{id}")
    suspend fun updateInfoPatient(
        @Path("id") id: Int,
        @Body infoPatient: PatientInfoModel
    ) : Response<UpdateInfoPatientResponse>

    @GET("appointments")
    suspend fun getListAppoint() : Response<AppointmentResponse>

    @GET("medical-history")
    suspend fun getListMedicalHistory() : Response<MedicalHistoryResponse>
}