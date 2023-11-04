package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.data.remote.entity.RegisterResponse
import com.example.appkhambenh.ui.data.remote.entity.UploadImageResponse
import com.example.appkhambenh.ui.model.Medicine
import com.example.appkhambenh.ui.model.RegisterChecking
import com.example.appkhambenh.ui.model.WorkingDate
import com.example.appkhambenh.ui.ui.doctor.department.DepartmentResponse
import com.example.appkhambenh.ui.ui.doctor.time_working.UpdateTimeResponse
import com.example.appkhambenh.ui.ui.user.appointment.register.ChangeStatusWorkingTimeResponse
import com.example.appkhambenh.ui.ui.user.appointment.register.RegisterAppointmentResponse
import com.example.appkhambenh.ui.ui.user.navigation.password.PasswordResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("account/login.php")
    fun loginUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ):Call<LoginResponse>

    @Multipart
    @POST("account/register.php")
    fun registerUser(
        @Part("email") email: RequestBody,
        @Part("sex") sex: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part("birth") birth: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("type") type: RequestBody
    ):Call<RegisterResponse>

    @GET("medicine.php")
    fun getListMedicine(): Observable<List<Medicine>>

    @Multipart
    @POST("account/upload_image.php")
    fun uploadImage(
        @Part("user_id") user_id: RequestBody,
        @Part("avatar") avatar: RequestBody
    ): Call<UploadImageResponse>

    @Multipart
    @POST("account/user_info.php")
    fun getUserInfo(
        @Part("id") id: RequestBody
    ): Call<UserInfoResponse>

    @Multipart
    @POST("account/update_info.php")
    fun updateInfo(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("sex") sex: RequestBody,
        @Part("birth") birth: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
    ): Observable<UserInfoResponse>

    @Multipart
    @POST("account/check_password.php")
    fun checkPassword(
        @Part("id") id: RequestBody,
        @Part("password") password: RequestBody
    ): Observable<PasswordResponse>

    @Multipart
    @POST("account/change_password.php")
    fun changePassword(
        @Part("id") id: RequestBody,
        @Part("password") password: RequestBody
    ): Observable<PasswordResponse>

    @Multipart
    @POST("time/update_working_time.php")
    fun updateWorkingTime(
        @Part("day") day: RequestBody,
        @Part("hour") hour: RequestBody
    ): Observable<UpdateTimeResponse>

    @Multipart
    @POST("time/working_time.php")
    fun getWorkingTime(
        @Part("day") day: RequestBody
    ): Observable<WorkingDate>

    @Multipart
    @POST("time/change_status_working_time.php")
    fun changeStatusWorkingTime(
        @Part("id_day") id_day: RequestBody,
        @Part("hour") hour: RequestBody,
        @Part("is_registered") is_registered: RequestBody
    ): Observable<ChangeStatusWorkingTimeResponse>

    @Multipart
    @POST("time/delete_working_time.php")
    fun deleteWorkingTime(
        @Part("id_day") id_day: RequestBody,
        @Part("hour") hour: RequestBody
    ): Observable<UpdateTimeResponse>

    @Multipart
    @POST("time/edit_working_time.php")
    fun editWorkingTime(
        @Part("id_day") id_day: RequestBody,
        @Part("hour") hour: RequestBody,
        @Part("new_hour") new_hour: RequestBody
    ): Observable<UpdateTimeResponse>

    @Multipart
    @POST("appointment/get_list_appointment.php")
    fun getListAppointment(
        @Part("id_user") id_user: RequestBody
    ): Observable<List<RegisterChecking>>

    @Multipart
    @POST("appointment/add_appointment.php")
    fun addAppointment(
        @Part("service") service: RequestBody,
        @Part("department") department: RequestBody,
        @Part("doctor") doctor: RequestBody,
        @Part("date") date: RequestBody,
        @Part("hour") hour: RequestBody,
        @Part("reasons") reasons: RequestBody,
        @Part("id_user") id_user: RequestBody
    ): Observable<RegisterAppointmentResponse>

    @Multipart
    @POST("appointment/delete_appointment.php")
    fun deleteAppoint(
        @Part("is_cancel") is_cancel: RequestBody,
        @Part("day") day: RequestBody,
        @Part("hour") hour: RequestBody,
        @Part("is_registered") is_registered: RequestBody
    ): Observable<RegisterAppointmentResponse>

    @Multipart
    @POST("appointment/edit_appointment.php")
    fun editAppoint(
        @Part("service") service: RequestBody,
        @Part("department") department: RequestBody,
        @Part("doctor") doctor: RequestBody,
        @Part("date") date: RequestBody,
        @Part("hour") hour: RequestBody,
        @Part("reasons") reasons: RequestBody,
        @Part("id_user") id_user: RequestBody
    ): Observable<RegisterAppointmentResponse>

    @Multipart
    @POST("department/add_department.php")
    fun addDepartment(
        @Part("name") name: RequestBody
    ): Observable<DepartmentResponse>
}