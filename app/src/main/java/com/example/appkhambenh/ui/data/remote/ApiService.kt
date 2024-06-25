package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.BookAppointmentResponse
import com.example.appkhambenh.ui.data.remote.entity.ChangePasswordResponse
import com.example.appkhambenh.ui.data.remote.entity.DoctorResponse
import com.example.appkhambenh.ui.data.remote.entity.HospitalResponse
import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.data.remote.entity.ProfileResponse
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.data.remote.entity.RegisterResponse
import com.example.appkhambenh.ui.data.remote.entity.SpecialistResponse
import com.example.appkhambenh.ui.data.remote.entity.TimeWorkingResponse
import com.example.appkhambenh.ui.data.remote.entity.UploadAvatarResponse
import com.example.appkhambenh.ui.data.remote.model.BookAppointmentModel
import com.example.appkhambenh.ui.data.remote.model.ChangePasswordModel
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.data.remote.model.ProfileModel
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun loginUser(
        @Body loginModel: LoginModel,
    ): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(
        @Body registerModel: RegisterModel,
    ): Response<RegisterResponse>

    @Multipart
    @POST("add-avatar/{id}")
    suspend fun updateAvatar(
        @Path("id") id: Int,
        @Part avatar: MultipartBody.Part,
    ): Response<UploadAvatarResponse>

    @GET("get-avatar/{id}")
    suspend fun getAvatar(@Path("id") id: Int): Response<ResponseBody>

    @GET("profile")
    suspend fun getUserInfo(): Response<UserInfoResponse>

    @GET("admin/hospital/get_list_hospital")
    suspend fun getListHospital(): Response<HospitalResponse>

    @GET("admin/specialist/list_specialist")
    suspend fun getListSpecialist(): Response<SpecialistResponse>

    @GET("get-list-employee?pageSize=40&page=1&sorts=1&name=")
    suspend fun getListDoctor(): Response<DoctorResponse>

    @PUT("update-profile/{id}")
    suspend fun updateProfile(
        @Body profileModel: ProfileModel,
        @Path("id") id: Int,
    ): Response<ProfileResponse>

    @GET("employee/get-list-calendar-working/{id}/{dateStart}")
    suspend fun getTimeWorking(
        @Path("id") id: Int,
        @Path("dateStart") dateStart: Long,
    ): Response<TimeWorkingResponse>

    @PUT("update-password/{id}")
    suspend fun changePassword(
        @Path("id") id: Int,
        @Body changePasswordModel: ChangePasswordModel,
    ): Response<ChangePasswordResponse>

    @POST("booking_customer/create_booking")
    suspend fun createBooking(
        @Body bookAppointmentModel: BookAppointmentModel
    ): Response<BookAppointmentResponse>

    @GET("booking_customer/get_list_booking?date_start=&date_end=&status=&page=&pageSize=")
    suspend fun getListBooking(): Response<BookAppointmentResponse>
}