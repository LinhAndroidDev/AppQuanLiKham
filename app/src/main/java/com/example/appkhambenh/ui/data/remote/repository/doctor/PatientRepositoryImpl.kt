package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.entity.DeletePatientResponse
import com.example.appkhambenh.ui.data.remote.entity.GetPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.ValueVitalChartResponse
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class PatientRepositoryImpl @Inject constructor(private val doctorService: DoctorService) :
    PatientRepository {
    override suspend fun getListPatient(
        fullname: String?,
        email: String?,
        citizenId: String?,
        healthInsurance: String?,
        phoneNumber: String?,
    ): Flow<PatientResponse> = flow {
        emit(doctorService.getListPatient(fullname, email, citizenId, healthInsurance, phoneNumber))
    }

    override suspend fun getPatient(patientId: Int): Flow<GetPatientResponse> = flow {
        emit(doctorService.getPatient(patientId))
    }

    override suspend fun updateInfoPatient(
        idUser: Int,
        infoPatient: PatientInfoModel,
    ): Flow<UpdateInfoPatientResponse> = flow {
        emit(doctorService.updateInfoPatient(idUser, infoPatient))
    }

    override suspend fun getValueVitalChart(patientId: Int): Flow<ValueVitalChartResponse> = flow {
        emit(doctorService.getValueVitalChart(patientId))
    }

    override suspend fun deletePatient(patientId: Int): Flow<DeletePatientResponse> = flow {
        emit(doctorService.deletePatient(patientId))
    }
}