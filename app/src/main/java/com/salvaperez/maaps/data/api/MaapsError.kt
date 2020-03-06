package com.salvaperez.maaps.data.api

import java.lang.Exception

sealed class MaapsError: Exception() {

    class ServerError: MaapsError()
    class Unknown: MaapsError()

}