package com.example.appkhambenh.ui.data.remote.di

import android.content.Context
import com.budiyev.android.codescanner.BuildConfig
import com.example.appkhambenh.ui.data.remote.ApiService
import com.example.appkhambenh.ui.data.remote.DoctorService
import com.example.appkhambenh.ui.data.remote.helper.Constants
import com.example.appkhambenh.ui.utils.SharePreferenceRepository
import com.example.appkhambenh.ui.utils.SharePreferenceRepositoryImpl
import com.example.appkhambenh.ui.utils.TokenManager
import com.google.android.gms.common.util.SharedPreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context.applicationContext

    @Provides
    @ViewModelScoped
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return loggingInterceptor
    }

    @Provides
    @ViewModelScoped
    fun provideOkHttpClient(logging: HttpLoggingInterceptor, context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val token = SharePreferenceRepositoryImpl(context).getAuthorization()
                val requestBuilder = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                requestBuilder.header("Authorization", "Bearer $token")
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                // Kiểm tra mã trạng thái phản hồi
                if (response.code == 401) {
                    // Xử lý token hết hạn (có thể gửi một sự kiện hoặc thông báo cho người dùng)
                    TokenManager.tokenExpiredEvent.value = !TokenManager.tokenExpiredEvent.value
                }

                response
            })
            .callTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @ViewModelScoped
    @ApiWebService
    fun provideApiClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    @Provides
    @ViewModelScoped
    fun provideNewsApi(@ApiWebService retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @ViewModelScoped
    @DoctorWebService
    fun provideDoctorClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.DOCTOR_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    @Provides
    @ViewModelScoped
    fun provideDoctorApi(@DoctorWebService retrofit: Retrofit): DoctorService {
        return retrofit.create(DoctorService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideSharePreference(context: Context): SharePreferenceRepository {
        return SharePreferenceRepositoryImpl(context)
    }
}