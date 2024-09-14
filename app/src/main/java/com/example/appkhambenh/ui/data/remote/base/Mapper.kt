package com.example.appkhambenh.ui.data.remote.base

interface Mapper<T, R> {
    fun fromMap(from: T): R
}