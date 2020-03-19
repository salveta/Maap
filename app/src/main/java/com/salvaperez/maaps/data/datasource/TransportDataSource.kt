package com.salvaperez.maaps.data.datasource

import com.salvaperez.maaps.data.entity.ErrorEntity
import com.salvaperez.maaps.data.entity.TransportsEntity
import com.salvaperez.maaps.data.api.MaapResult

interface TransportDataSource {

    suspend fun getTransport(): MaapResult<ErrorEntity, List<TransportsEntity>>

}