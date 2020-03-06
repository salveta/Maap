package com.salvaperez.maaps.data.api

import com.salvaperez.maaps.data.entity.ErrorEntity
import com.salvaperez.maaps.data.entity.toErrorEntity
import retrofit2.HttpException

object ApiCall {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): MaapResult<ErrorEntity, T> {
        return try {
            val result = apiCall()
            MaapResult.Success(result)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val errorEntity = throwable.toErrorEntity()
                MaapResult.Failure(errorEntity)
            } else {
                MaapResult.Failure(ErrorEntity.unknownEntity)
            }
        }
    }
}