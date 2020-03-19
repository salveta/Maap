package com.salvaperez.maaps.data.repository

import com.salvaperez.maaps.domain.repository.TransportRepository
import com.salvaperez.maaps.data.datasource.RemoteTransportDataSource
import com.salvaperez.maaps.data.api.MaapResult
import com.salvaperez.maaps.data.api.MaapsError
import com.salvaperez.maaps.data.api.fold
import com.salvaperez.maaps.domain.model.TransportsModel
import com.salvaperez.maaps.domain.model.toMaapsError
import com.salvaperez.maaps.domain.model.toModel

class TransportDataRepository(private val remoteRankingDataSource: RemoteTransportDataSource):
    TransportRepository {

    override suspend fun getTransports(): MaapResult<MaapsError, List<TransportsModel>> {
        val result = remoteRankingDataSource.getTransport()

        return result.fold(
            { errorEntity -> MaapResult.Failure(errorEntity.toMaapsError()) },
            { dataEntity ->
                MaapResult.Success(dataEntity.map { it.toModel() })
            }
        )
    }
}