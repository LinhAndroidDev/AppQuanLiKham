package com.example.appkhambenh.ui.model

import com.example.appkhambenh.ui.utils.checkDateAppoint
import java.util.*

data class RegisterChecking(
    val service: String? = null,
    val department: String? = null,
    val doctor: String? = null,
    val date: String? = null,
    val hour: String? = null,
    val reasons: String? = null,
    val id_user: Int? = null,
    val is_expired: Int? = null
): java.io.Serializable{

    fun checkAppointExpired(): Boolean{
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return checkDateAppoint("$hour $date $currentYear")
    }
}

