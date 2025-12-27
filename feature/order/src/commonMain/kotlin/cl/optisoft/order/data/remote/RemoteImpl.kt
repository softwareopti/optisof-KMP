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
                        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY5NDliMzA4MDZlNDgzN2Q0NzM1YzMzMSIsInByb2ZpbGVJZCI6IjY4YWRiYjY1YzQwZTc5MGU2NGRmMTFkZCIsImlhdCI6MTc2Njg0NTM3NCwiZXhwIjoxNzY2ODc0MTc0fQ.OPEkMb4TtCQCaWjaRFoQ0F4MU3JCoSbJOnG6gq2b15o"
                    )
                }
            }
        }
    }
}