package com.ugurbuga.followtvmovie.core.base

import com.ugurbuga.followtvmovie.core.BuildConfig
import com.ugurbuga.followtvmovie.core.common.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository(private val dispatchers: CoroutineDispatcher) {



    fun <T : Any> onApiCall(call: suspend () -> T): Flow<ApiState<T>> =
        flow {
            emit(ApiState.Loading)
            emit(ApiState.Success(data = call.invoke()))
        }.catch { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
            recordException(error)
            emit(ApiState.Error(error))
        }.flowOn(dispatchers)

    private fun recordException(error: Throwable) {
        //Firebase.crashlytics.recordException(error)
    }

    fun <T : Any> onRoomCall(call: suspend () -> T): Flow<ApiState<T>> =
        flow {
            emit(ApiState.Loading)
            emit(ApiState.Success(data = call.invoke()))
        }.catch { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
            recordException(error)
            emit(ApiState.Error(error))
        }.flowOn(dispatchers)

    fun <T : Any?> onRoomFlowCall(call: Flow<T>): Flow<ApiState<T>> =
        flow {
            emit(ApiState.Loading)
            call.collect {
                emit(ApiState.Success(it))
            }
        }.catch {
            recordException(Throwable("onRoomFlowCall"))
            emit(ApiState.Error(it))
        }.flowOn(dispatchers)
}
