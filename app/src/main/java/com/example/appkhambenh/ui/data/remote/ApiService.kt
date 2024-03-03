package com.example.appkhambenh.ui.data.remote

import com.example.appkhambenh.ui.data.remote.entity.HospitalResponse
import com.example.appkhambenh.ui.data.remote.entity.LoginResponse
import com.example.appkhambenh.ui.data.remote.entity.UserInfoResponse
import com.example.appkhambenh.ui.data.remote.entity.RegisterResponse
import com.example.appkhambenh.ui.data.remote.entity.UploadImageResponse
import com.example.appkhambenh.ui.data.remote.model.LoginModel
import com.example.appkhambenh.ui.data.remote.model.RegisterModel
import com.example.appkhambenh.ui.model.Medicine
import com.example.appkhambenh.ui.ui.user.navigation.password.PasswordResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("login")
    suspend fun loginUser(
        @Body loginModel: LoginModel,
    ): Response<LoginResponse>

    @POST("register")
    suspend fun registerUser(
        @Body registerModel: RegisterModel,
    ): Response<RegisterResponse>

    @GET("medicine.php")
    fun getListMedicine(): Observable<List<Medicine>>

    @Multipart
    @POST("account/upload_image.php")
    fun uploadImage(
        @Part("user_id") user_id: RequestBody,
        @Part("avatar") avatar: RequestBody,
    ): Call<UploadImageResponse>


    @GET("profile")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserInfoResponse>

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
        @Part("password") password: RequestBody,
    ): Observable<PasswordResponse>

    @Multipart
    @POST("account/change_password.php")
    fun changePassword(
        @Part("id") id: RequestBody,
        @Part("password") password: RequestBody,
    ): Observable<PasswordResponse>

    @GET("admin/hospital/get_list_hospital")
    suspend fun getListHospital(): Response<HospitalResponse>
}