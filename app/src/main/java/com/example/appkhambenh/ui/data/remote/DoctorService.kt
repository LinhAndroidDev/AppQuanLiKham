package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.AccountResponse
import com.example.appkhambenh.ui.data.remote.entity.AddAccountResponse
import com.example.appkhambenh.ui.data.remote.entity.AddMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.AppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.DoctorLoginResponse
import com.example.appkhambenh.ui.data.remote.entity.MedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import com.example.appkhambenh.ui.data.remote.entity.AddServiceResponse
import com.example.appkhambenh.ui.data.remote.entity.ConfirmAppointResponse
import com.example.appkhambenh.ui.data.remote.entity.DeletePatientResponse
import com.example.appkhambenh.ui.data.remote.entity.DiagnoseResponse
import com.example.appkhambenh.ui.data.remote.entity.GetMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.GetPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.HospitalDischargeResponse
import com.example.appkhambenh.ui.data.remote.entity.PayServiceResponse
import com.example.appkhambenh.ui.data.remote.entity.ServiceOrderResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateAllocationResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateChartResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateDiagnoseMedicalHistoryResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoClinicalExaminationResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.ValueVitalChartResponse
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import com.example.appkhambenh.ui.data.remote.request.AddAccountRequest
import com.example.appkhambenh.ui.data.remote.request.AddMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.AddServiceRequest
import com.example.appkhambenh.ui.data.remote.request.BloodTestRequest
import com.example.appkhambenh.ui.data.remote.request.ConfirmAppointRequest
import com.example.appkhambenh.ui.data.remote.request.DiagnoseRequest
import com.example.appkhambenh.ui.data.remote.request.PayServiceRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateAllocationRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateChartRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateDiagnoseMedicalHistoryRequest
import com.example.appkhambenh.ui.data.remote.request.UpdateInfoClinicalExaminationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    ): DoctorLoginResponse

    @GET("patients")
    suspend fun getListPatient(
        @Query("fullname") fullname: String?,
        @Query("email") email: String?,
        @Query("citizenId") citizenId: String?,
        @Query("healthInsurance") healthInsurance: String?,
        @Query("phoneNumber") phoneNumber: String?
    ) : PatientResponse

    @GET("patients/{patientId}")
    suspend fun getPatient(
        @Path("patientId") patientId: Int
    ) : GetPatientResponse

    @PUT("patients/{id}")
    suspend fun updateInfoPatient(
        @Path("id") id: Int,
        @Body infoPatient: PatientInfoModel
    ) : UpdateInfoPatientResponse

    @DELETE("patients/{patientId}")
    suspend fun deletePatient(
        @Path("patientId") patientId: Int
    ): DeletePatientResponse

    @GET("patients/{patientId}/vital-chart")
    suspend fun getValueVitalChart(
        @Path("patientId") patientId: Int
    ): ValueVitalChartResponse

    @GET("appointments")
    suspend fun getListAppoint(
        @Query("time") time: String?
    ) : AppointmentResponse

    @PUT("appointments/{id}")
    suspend fun confirmAppoint(
        @Path("id") id: Int,
        @Body confirmAppointRequest: ConfirmAppointRequest = ConfirmAppointRequest()
    ): ConfirmAppointResponse

    @GET("medical-history")
    suspend fun getListMedicalHistory(
        @Query("patientId") patientId: Int?
    ): MedicalHistoryResponse

    @GET("medical-history/{patientId}")
    suspend fun getMedicalHistory(
        @Path("patientId") patientId: Int
    ): GetMedicalHistoryResponse

    @POST("medical-history")
    suspend fun addMedicalHistory(
        @Body addMedicalHistoryRequest: AddMedicalHistoryRequest
    ): AddMedicalHistoryResponse

    @PUT("medical-history/{medicalHistoryId}/diagnose")
    suspend fun updateDiagnoseMedicalHistory(
        @Path("medicalHistoryId") medicalHistoryId: Int,
        @Body updateDiagnoseMedicalHistoryRequest: UpdateDiagnoseMedicalHistoryRequest
    ): UpdateDiagnoseMedicalHistoryResponse

    @PUT("medical-history/{medicalHistoryId}/treatment")
    suspend fun updateAllocation(
        @Path("medicalHistoryId") medicalHistoryId: Int,
        @Body updateAllocationRequest: UpdateAllocationRequest
    ): UpdateAllocationResponse

    @PUT("medical-history/{medicalHistoryId}/hospital-discharge")
    suspend fun hospitalDischarge(
        @Path("medicalHistoryId") medicalHistoryId: Int
    ): HospitalDischargeResponse

    @GET("service-order/{id}")
    suspend fun getServiceOrder(
        @Path("id") id: Int
    ) : ServiceOrderResponse

    @PUT("service-order/status/{id}")
    suspend fun payService(
        @Path("id") id: Int,
        @Body payServiceRequest: PayServiceRequest = PayServiceRequest()
    ) : PayServiceResponse

    @POST("service-order")
    suspend fun addService(
        @Body addServiceRequest: AddServiceRequest
    ): AddServiceResponse

    @PUT("service-order/{id}")
    suspend fun updateChart(
        @Path("id") id: Int,
        @Body updateChartRequest: UpdateChartRequest
    ): UpdateChartResponse

    @PUT("service-order/{serviceMedicalHistoryId}")
    suspend fun updateClinicalExamination(
        @Path("serviceMedicalHistoryId") serviceMedicalHistoryId: Int,
        @Body updateInfoClinicalExaminationRequest: UpdateInfoClinicalExaminationRequest
    ): UpdateInfoClinicalExaminationResponse

    @PUT("service-order/{serviceMedicalHistoryId}")
    suspend fun updateBloodTest(
        @Path("serviceMedicalHistoryId") serviceMedicalHistoryId: Int,
        @Body updateBloodTestRequest: BloodTestRequest
    ): UpdateInfoClinicalExaminationResponse

    @PUT("service-order/{serviceMedicalHistoryId}")
    suspend fun updateDiagnose(
        @Path("serviceMedicalHistoryId") serviceMedicalHistoryId: Int,
        @Body diagnoseRequest: DiagnoseRequest
    ): DiagnoseResponse

    @GET("auth/users")
    suspend fun getAccount(
        @Query("fullname") fullname: String?,
        @Query("email") email: String?,
        @Query("roleId") roleId: Int?
    ): AccountResponse

    @POST("auth/register")
    suspend fun addAccount(
        @Body addAccountRequest: AddAccountRequest
    ): AddAccountResponse
}