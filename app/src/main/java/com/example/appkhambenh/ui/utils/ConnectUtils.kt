@file:Suppress("DEPRECATION")

package com.example.appkhambenh.ui.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isOnline(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}