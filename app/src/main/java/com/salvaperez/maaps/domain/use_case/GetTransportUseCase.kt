package com.salvaperez.maaps.domain.use_case

import com.salvaperez.maaps.data.api.fold
import com.salvaperez.maaps.domain.repository.TransportRepository
import com.salvaperez.maaps.domain.model.TransportsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTransportUseCase(private val transportRepository: TransportRepository) {

    suspend operator fun invoke(
        onGetTransportSuccess: (data: List<TransportsModel>?) -> Unit,
        onGetErrorTransport: () -> Unit
    ) {

        val result = withContext(Dispatchers.IO){
            transportRepository.getTransports()
        }

        result.fold(
            failure = { onGetErrorTransport() },
            success = { data -> onGetTransportSuccess(data) }
        )
    }
}