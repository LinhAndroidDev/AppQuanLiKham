package com.example.appkhambenh.ui.utils

import android.app.Activity
import com.example.appkhambenh.ui.model.Address
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun getDataProvince(context: Activity): HashMap<String, Address> {
    val jsonFileName = "tinh_tp.json"
    val jsonString = context.assets.open(jsonFileName).bufferedReader().use { it.readText() }
    val gson = Gson()
    val listPersonType = object : TypeToken<HashMap<String, Address>>() {}.type
    return gson.fromJson(jsonString, listPersonType)
}

fun getDataDistrict(context: Activity): HashMap<String, Address> {
    val jsonFileName = "quan_huyen.json"
    val jsonString = context.assets.open(jsonFileName).bufferedReader().use { it.readText() }
    val gson = Gson()
    val listPersonType = object : TypeToken<HashMap<String, Address>>() {}.type
    return gson.fromJson(jsonString, listPersonType)
}

fun getDataWard(context: Activity): HashMap<String, Address> {
    val jsonFileName = "xa_phuong.json"
    val jsonString = context.assets.open(jsonFileName).bufferedReader().use { it.readText() }
    val gson = Gson()
    val listPersonType = object : TypeToken<HashMap<String, Address>>() {}.type
    return gson.fromJson(jsonString, listPersonType)
}