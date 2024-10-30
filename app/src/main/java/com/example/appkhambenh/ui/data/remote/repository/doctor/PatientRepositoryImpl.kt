package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.base.ApiState
import com.example.appkhambenh.ui.data.remote.base.BaseRepository
import com.example.appkhambenh.ui.data.remote.entity.DeletePatientResponse
import com.example.appkhambenh.ui.data.remote.entity.GetPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.ValueVitalChartResponse
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class PatientRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    PatientRepository, BaseRepository() {
    override suspend fun getListPatient(
        fullname: String?,
        email: String?,
        citizenId: String?,
        healthInsurance: String?,
        phoneNumber: String?,
    ): Flow<ApiState<PatientResponse>> = safeApiCall {
        doctorService.getListPatient(fullname, email, citizenId, healthInsurance, phoneNumber)
    }

    override suspend fun getPatient(patientId: Int): Flow<ApiState<GetPatientResponse>> =
        safeApiCall {
            doctorService.getPatient(patientId)
        }

    override suspend fun updateInfoPatient(
        idUser: Int,
        infoPatient: PatientInfoModel,
    ): Flow<ApiState<UpdateInfoPatientResponse>> = safeApiCall {
        doctorService.updateInfoPatient(idUser, infoPatient)
    }

    override suspend fun getValueVitalChart(patientId: Int): Flow<ApiState<ValueVitalChartResponse>> =
        safeApiCall {
            doctorService.getValueVitalChart(patientId)
        }

    override suspend fun deletePatient(patientId: Int): Flow<ApiState<DeletePatientResponse>> =
        safeApiCall {
            doctorService.deletePatient(patientId)
        }
}