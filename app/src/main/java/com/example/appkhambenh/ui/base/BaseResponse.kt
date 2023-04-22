package com.example.appkhambenh.ui.base

abstract class BaseResponse<R> {
    val message: String? = null
    val statusCode: Int? = null
    abstract val result: R?
}