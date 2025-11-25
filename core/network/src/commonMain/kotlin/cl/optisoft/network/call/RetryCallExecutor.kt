package cl.optisoft.network.call

import cl.optisoft.common.response.Response
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onSuccess
import cl.optisoft.network.response.NetworkErrors


object RetryCallExecutor {
    suspend fun <D> call(
        times: Int = 3,
        block: suspend () -> Response<D, NetworkErrors>
    ): Response<D, NetworkErrors> {
        var currentAttempt = 0
        var lastData: Response<D, NetworkErrors> = Response.Loading

        while (currentAttempt < times) {
            val data = block()
            lastData = data.onSuccess {
                return Response.Success(it)
            }.onError {
                currentAttempt++
            }
        }

        return lastData
    }
}