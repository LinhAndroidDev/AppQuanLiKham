package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.AddMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import com.example.appkhambenh.ui.data.remote.entity.AddServiceResponse
import com.example.appkhambenh.ui.data.remote.entity.ConfirmAppointResponse
import com.example.appkhambenh.ui.data.remote.entity.PayServiceResponse
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateChartResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateDiagnoseMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoClinicalExaminationResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.ValueVitalChartResponse
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.BloodTestRequest
import com.example.appkhambenh.ui.data.remote.request.ConfirmAppointRequest
import com.example.appkhambenh.ui.data.remote.request.PayServiceRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("patients/{patientId}/vital-chart")
    suspend fun getValueVitalChart(
        @Path("patientId") patientId: Int
    ): Response<ValueVitalChartResponse>

    @GET("appointments")
    suspend fun getListAppoint() : Response<AppointmentResponse>

    @PUT("appointments/{id}")
    suspend fun confirmAppoint(
        @Path("id") id: Int,
        @Body confirmAppointRequest: ConfirmAppointRequest = ConfirmAppointRequest()
    ): Response<ConfirmAppointResponse>

    @GET("medical-history")
    suspend fun getListMedicalHistory(
        @Query("patientId") patientId: Int?
    ): Response<MedicalHistoryResponse>

    @POST("medical-history")
    suspend fun addMedicalHistory(
        @Body addMedicalHistoryRequest: AddMedicalHistoryRequest
    ): Response<AddMedicalHistoryResponse>

    @PUT("medical-history/{id}/diagnose")
    suspend fun updateDiagnoseMedicalHistory(
        @Path("id") patientId: Int,
        @Body updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest
    ): Response<UpdateDiagnoseMedicalHistoryResponse>

    @GET("service-order/{id}")
    suspend fun getServiceOrder(
        @Path("id") id: Int
    ) : Response<ServiceOrderResponse>

    @PUT("service-order/status/{id}")
    suspend fun payService(
        @Path("id") id: Int,
        @Body payServiceRequest: PayServiceRequest = PayServiceRequest()
    ) : Response<AddServiceResponse>

    @POST("service-order")
    suspend fun addService(
        @Body addServiceRequest: AddServiceRequest
    ): Response<PayServiceResponse>

    @PUT("service-order/{id}")
    suspend fun updateChart(
        @Path("id") id: Int,
        @Body updateChartRequest: UpdateChartRequest
    ): Response<UpdateChartResponse>

    @PUT("service-order/{serviceMedicalHistoryId}")
    suspend fun updateClinicalExamination(
        @Path("serviceMedicalHistoryId") serviceMedicalHistoryId: Int,
        @Body updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest
    ): Response<UpdateInfoClinicalExaminationResponse>

    @PUT("service-order/{serviceMedicalHistoryId}")
    suspend fun updateBloodTest(
        @Path("serviceMedicalHistoryId") serviceMedicalHistoryId: Int,
        @Body updateBloodTestRequest: BloodTestRequest
    ): Response<UpdateInfoClinicalExaminationResponse>
}