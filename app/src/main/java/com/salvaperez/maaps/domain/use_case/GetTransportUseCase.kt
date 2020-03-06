package com.salvaperez.maaps.domain.use_case

import com.salvaperez.maaps.domain.repository.TransportRepository
import com.salvaperez.maaps.data.api.fold
import com.salvaperez.maaps.domain.model.TransportsModel
import kotlinx.coroutines.*

class GetTransportUseCase(private val rankingRepository: TransportRepository) {

    operator fun invoke(
        onGetTransportSuccess: (data: List<TransportsModel>?) -> Unit,
        onGetErrorTransport: () -> Unit
    ) {

        val deferred = GlobalScope.async(Dispatchers.IO) {
            rankingRepository.getTransports()
        }
        GlobalScope.launch {
            val result = deferred.await()
            withContext(Dispatchers.Main) {
                result.fold(
                    failure = { onGetErrorTransport()},
                    success = { data -> onGetTransportSuccess(data) }
                )
            }
        }
    }
}