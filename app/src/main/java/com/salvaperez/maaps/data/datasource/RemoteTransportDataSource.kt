package com.salvaperez.maaps.data.datasource

import com.salvaperez.maaps.data.entity.ErrorEntity
import com.salvaperez.maaps.data.api.ApiCall
import com.salvaperez.maaps.data.entity.TransportsEntity
import com.salvaperez.maaps.data.api.MaapResult
import com.salvaperez.maaps.data.api.MaapsApi

class RemoteTransportDataSource(private val api: MaapsApi):
    TransportDataSource {

    override suspend fun getRanking(): MaapResult<ErrorEntity, List<TransportsEntity>> {
        return ApiCall.safeApiCall { api.getTransports() }
    }
}