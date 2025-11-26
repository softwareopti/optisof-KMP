package cl.optisoft.order.data.repository

import cl.optisoft.common.response.Response
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.model.RecommendationResponse

internal interface Remote {
    suspend fun getAllRecommendations() : Response<List<RecommendationResponse>, NetworkErrors>
}