package com.salvaperez.maaps.data.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException

data class ErrorEntity(
    val code: Int,
    @SerializedName("error") val error: String,
    @SerializedName("error_description") val description: String
) {

    companion object {
        const val UNKNOWN_ERROR = 99
        val unknownEntity = ErrorEntity(
            UNKNOWN_ERROR,
            "Unknown",
            "Unknown error"
        )
    }
}

fun HttpException.toErrorEntity(): ErrorEntity {
    return try {
        response()?.errorBody().let {
            Gson().fromJson(it?.string(), ErrorEntity::class.java)?.copy(code = code())
        } ?: ErrorEntity.unknownEntity
    } catch (exception: Exception) {
        ErrorEntity.unknownEntity
    }
}