package com.example.appkhambenh.ui.utils

import android.text.TextUtils
import android.util.Patterns

data class Email(val email: String, val password: String) {
    fun isValidEmail(): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

    fun isValidPhone(): Boolean {
        return !TextUtils.isEmpty(email) && (Patterns.PHONE.matcher(email)
            .matches() && email.length >= 10)
    }

    fun isPassword(): Boolean {
        return !TextUtils.isEmpty(password) && password.length >= 7
    }
}