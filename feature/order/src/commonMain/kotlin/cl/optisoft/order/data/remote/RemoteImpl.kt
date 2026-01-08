package cl.optisoft.order.data.remote

import cl.optisoft.common.response.Response
import cl.optisoft.network.call.SafeApiCall
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.model.RecommendationResponse
import cl.optisoft.order.data.repository.Remote
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

internal class RemoteImpl(private val httpClient: HttpClient) : Remote {
    override suspend fun getAllRecommendations(): Response<List<RecommendationResponse>, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.get("/api/recommendation/all"){
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5NWQxOTE0ZWZkZDY1YTc3OTM2MjFmNCIsInByb2ZpbGVJZCI6IjY4YWRiYjY1YzQwZTc5MGU2NGRmMTFkZCIsImlhdCI6MTc2Nzg4NDExMywiZXhwIjoxNzY3OTEyOTEzfQ.l4xc28md_e7i-biR79_YXZP3Y39RIpCFvZQZOfVUDqg"
                    )
                }
            }
        }
    }
}