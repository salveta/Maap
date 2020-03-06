package com.salvaperez.maaps.data.api


sealed class MaapResult<out Failure, out Success> {

    data class Failure<out Failure>(val failure: Failure) : MaapResult<Failure, Nothing>()

    data class Success<out Success>(val success: Success) : MaapResult<Nothing, Success>()

    val isSuccess get() = this is MaapResult.Success<Success>
    val isFailure get() = this is MaapResult.Failure<Failure>

}

fun <Failure, Success, T> MaapResult<Failure, Success>.fold(failure: (Failure) -> T, success: (Success) -> T): T =
    when (this) {
        is MaapResult.Failure -> failure(this.failure)
        is MaapResult.Success -> success(this.success)
    }