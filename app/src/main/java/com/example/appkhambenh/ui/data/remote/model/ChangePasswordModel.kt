package com.example.appkhambenh.ui.data.remote.model

import com.google.gson.annotations.SerializedName

class ChangePasswordModel(
    @SerializedName("old_password")
    val oldPassword: String?,

    @SerializedName("new_password")
    val newPassword: String?
)