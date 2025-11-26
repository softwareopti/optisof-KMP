package cl.optisoft.order.data

import cl.optisoft.common.response.Response
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.model.RecommendationResponse
import cl.optisoft.order.data.source.Factory

internal class DataRepository (private val factory: Factory) {

    suspend fun getAllRecommendations() : Response<List<RecommendationResponse>, NetworkErrors> {
        return factory.getRemote().getAllRecommendations()
    }
}