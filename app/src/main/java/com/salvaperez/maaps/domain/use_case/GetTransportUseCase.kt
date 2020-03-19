package com.salvaperez.maaps.domain.use_case

import com.salvaperez.maaps.data.api.fold
import com.salvaperez.maaps.domain.repository.TransportRepository
import com.salvaperez.maaps.domain.model.TransportsModel

class GetTransportUseCase(private val transportRepository: TransportRepository) {

    suspend operator fun invoke(
        onGetTransportSuccess: (data: List<TransportsModel>?) -> Unit,
        onGetErrorTransport: () -> Unit
    ) {

        transportRepository.getTransports().fold(
            failure = { onGetErrorTransport() },
            success = { data -> onGetTransportSuccess(data) }
        )
    }
}