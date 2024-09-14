package com.example.appkhambenh.ui.data.remote.entity

import com.example.appkhambenh.ui.data.remote.model.AccountModel

data class AccountResponse(
    val data: ArrayList<AccountModel>,
    val totalPatient: Int,
    val paginate: Paginate
)