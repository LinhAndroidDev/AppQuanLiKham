package com.example.appkhambenh.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow

object TokenManager {
    val tokenExpiredEvent = MutableStateFlow(false)
}