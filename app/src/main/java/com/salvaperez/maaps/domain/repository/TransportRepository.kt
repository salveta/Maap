package com.salvaperez.maaps.domain.repository

import com.salvaperez.maaps.data.api.MaapResult
import com.salvaperez.maaps.data.api.MaapsError
import com.salvaperez.maaps.domain.model.TransportsModel

interface TransportRepository {

    suspend fun getTransports(): MaapResult<MaapsError, List<TransportsModel>>

}