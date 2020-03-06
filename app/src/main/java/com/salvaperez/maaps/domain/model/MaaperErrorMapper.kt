package com.salvaperez.maaps.domain.model

import com.salvaperez.maaps.data.api.MaapsError
import com.salvaperez.maaps.data.entity.ErrorEntity

fun ErrorEntity.toMaapsError(): MaapsError {
    return when (code) {
        500 -> MaapsError.ServerError()
        else -> MaapsError.Unknown()
    }
}