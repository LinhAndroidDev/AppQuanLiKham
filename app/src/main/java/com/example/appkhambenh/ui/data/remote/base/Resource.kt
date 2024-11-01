package com.example.appkhambenh.ui.data.remote.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class ApiState<out T> {
    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val msg: Throwable) : ApiState<Nothing>()
    data class Loading(val isLoading: Boolean) : ApiState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success $data"
            is Failure -> "Failure $msg"
            else -> "Loading"
        }
    }
}

fun <T, R> ApiState<T>.map(transform: (T) -> R): ApiState<R> {
    return when(this) {
        is ApiState.Success -> ApiState.Success(transform(this.data))
        is ApiState.Failure -> ApiState.Failure(this.msg)
        is ApiState.Loading -> ApiState.Loading(this.isLoading)
    }
}

fun <T> Flow<ApiState<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<ApiState<T>> = transform { result ->
    if(result is ApiState.Success) {
        action(result.data)
    }
    return@transform emit(result)
}

fun <T> Flow<ApiState<T>>.doOnFailure(action: suspend (Throwable?) -> Unit): Flow<ApiState<T>> = transform { result ->
    if(result is ApiState.Failure) {
        action(result.msg)
    }
    return@transform emit(result)
}

fun <T> Flow<ApiState<T>>.doOnLoading(action: suspend (Boolean) -> Unit): Flow<ApiState<T>> = transform { result ->
    if(result is ApiState.Loading) {
        action(result.isLoading)
    }
    return@transform emit(result)
}