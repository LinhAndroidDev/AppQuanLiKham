package com.example.appkhambenh.ui.data.remote.request

data class AddAccountRequest(
    val email: String,
    val password: String = "W5!MBlei",
    val roleId: Int
)