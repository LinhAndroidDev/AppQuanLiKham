package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.data.remote.entity.RegisterResponse
import com.example.appkhambenh.ui.data.remote.entity.UploadImageResponse
import com.example.appkhambenh.ui.model.Medicine
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.model.WorkingDate
import com.example.appkhambenh.ui.ui.doctor.time_working.UpdateTimeResponse
import com.example.appkhambenh.ui.ui.user.appointment.register.RegisterAppointmentResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("login.php")
    fun loginUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ):Call<LoginResponse>

    @Multipart
    @POST("register.php")
    fun registerUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part("birth") birth: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody
    ):Call<RegisterResponse>

    @GET("medicine.php")
    fun getListMedicine(): Observable<List<Medicine>>

    @GET("working_time.php")
    fun getWorkingDate(): Call<ArrayList<WorkingDate>>

    @Multipart
    @POST("upload_image.php")
    fun uploadImage(
        @Part("user_id") user_id: RequestBody,
        @Part("avatar") avatar: RequestBody
    ): Call<UploadImageResponse>

    @Multipart
    @POST("user_info.php")
    fun getUserInfo(
        @Part("id") id: RequestBody
    ): Call<UserInfoResponse>

    @Multipart
    @POST("update_working_time.php")
    fun updateWorkingTime(
        @Part("day") day: RequestBody,
        @Part("hour") hour: RequestBody
    ): Observable<UpdateTimeResponse>

    @Multipart
    @POST("working_time.php")
    fun getWorkingTime(
        @Part("day") day: RequestBody
    ): Observable<WorkingDate>

    @GET("get_list_appointment.php")
    fun getListAppointment(): Observable<List<RegisterChecking>>

    @Multipart
    @POST("add_appointment.php")
    fun addAppointment(
        @Part("service") service: RequestBody,
        @Part("department") department: RequestBody,
        @Part("doctor") doctor: RequestBody,
        @Part("date") date: RequestBody,
        @Part("hour") hour: RequestBody,
        @Part("reasons") reasons: RequestBody,
    ): Observable<RegisterAppointmentResponse>
}