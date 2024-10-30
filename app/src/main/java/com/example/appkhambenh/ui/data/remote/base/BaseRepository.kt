package com.example.appkhambenh.ui.data.remote.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * This class is abstract base handle actions call api include loading, failure, success to handle actions in view model
 */
abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<ApiState<T>> = flow {
        emit(ApiState.Loading(true))
        try {
            val response = apiCall()
            emit(ApiState.Loading(false))
            if(response.isSuccessful) {
                emit(ApiState.Success(response.body()!!))
            } else {
                emit(ApiState.Failure(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(ApiState.Loading(false))
            emit(ApiState.Failure(Exception(e)))
        }
    }.catch { e ->
        emit(ApiState.Loading(false))
        emit(ApiState.Failure(Exception(e)))
    }.flowOn(Dispatchers.IO)
}