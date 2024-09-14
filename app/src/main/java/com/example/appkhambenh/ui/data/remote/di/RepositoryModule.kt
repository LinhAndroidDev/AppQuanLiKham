package com.example.appkhambenh.ui.data.remote.di

import com.example.appkhambenh.ui.data.remote.repository.doctor.AccountRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.AccountRepositoryImpl
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.AppointmentRepositoryImpl
import com.example.appkhambenh.ui.data.remote.repository.doctor.LoginDoctorRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.LoginDoctorRepositoryImpl
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.MedicalHistoryRepositoryImpl
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.PatientRepositoryImpl
import com.example.appkhambenh.ui.data.remote.repository.doctor.ServiceOrderRepository
import com.example.appkhambenh.ui.data.remote.repository.doctor.ServiceOrderRepositoryImpl
import com.example.appkhambenh.ui.data.remote.repository.user.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideLoginDoctorRepository(loginDoctorRepository: LoginDoctorRepositoryImpl): LoginDoctorRepository

    @Binds
    abstract fun provideAccountRepository(accountRepository: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun provideAppointmentRepository(appointmentRepository: AppointmentRepositoryImpl): AppointmentRepository

    @Binds
    abstract fun provideMedicalHistoryRepository(medicalHistoryRepository: MedicalHistoryRepositoryImpl): MedicalHistoryRepository

    @Binds
    abstract fun providePatientRepository(patientRepository: PatientRepositoryImpl): PatientRepository

    @Binds
    abstract fun provideServiceOrderRepository(serviceOrderRepository: ServiceOrderRepositoryImpl): ServiceOrderRepository
}