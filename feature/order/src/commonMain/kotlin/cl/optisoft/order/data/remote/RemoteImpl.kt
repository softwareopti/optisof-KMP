package cl.optisoft.order.data.remote

import cl.optisoft.common.response.Response
import cl.optisoft.network.call.SafeApiCall
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.model.RecommendationResponse
import cl.optisoft.order.data.repository.Remote
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class RemoteImpl(private val httpClient: HttpClient) : Remote {
    override suspend fun getAllRecommendations(): Response<List<RecommendationResponse>, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.get("/api/recommendation/all")
        }
    }
}