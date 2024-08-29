package com.gajanan.moivezapp.cache.database

import com.gajanan.moivezapp.utils.ResultApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType,RequestType> networkBoundResource(
    crossinline query : () -> Flow<ResultType>,
    crossinline fetch : suspend () -> RequestType,
    crossinline saveFetchResult : suspend (RequestType) -> Unit,
    crossinline shouldFetch : (ResultType) -> Boolean = { true }
) = flow {

    val data = query().first()
    val flow =  if (shouldFetch(data)){
        emit(ResultApi.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { ResultApi.Success(it) }
        }catch (e:Throwable){
            query().map { ResultApi.Error(e,it) }
        }

    }else{
        query().map { ResultApi.Success(it) }
    }
    emitAll(flow)
}