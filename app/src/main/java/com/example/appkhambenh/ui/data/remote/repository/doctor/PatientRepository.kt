package com.example.appkhambenh.ui.data.remote.repository.doctor

import com.example.appkhambenh.ui.data.remote.entity.DeletePatientResponse
import com.example.appkhambenh.ui.data.remote.entity.GetPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.PatientResponse
import com.example.appkhambenh.ui.data.remote.entity.UpdateInfoPatientResponse
import com.example.appkhambenh.ui.data.remote.entity.ValueVitalChartResponse
import com.example.appkhambenh.ui.data.remote.model.PatientInfoModel
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    suspend fun getListPatient(
        fullname: String? = null,
        email: String? = null,
        citizenId: String? = null,
        healthInsurance: String? = null,
        phoneNumber: String? = null,
    ): Flow<PatientResponse>

    suspend fun getPatient(patientId: Int): Flow<GetPatientResponse>

    suspend fun updateInfoPatient(
        idUser: Int,
        infoPatient: PatientInfoModel,
    ): Flow<UpdateInfoPatientResponse>

    suspend fun getValueVitalChart(patientId: Int): Flow<ValueVitalChartResponse>

    suspend fun deletePatient(patientId: Int): Flow<DeletePatientResponse>
}