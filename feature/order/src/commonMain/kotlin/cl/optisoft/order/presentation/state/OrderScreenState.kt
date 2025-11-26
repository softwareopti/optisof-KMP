package cl.optisoft.order.presentation.state

import androidx.compose.runtime.Immutable
import cl.optisoft.order.data.model.RecommendationResponse

@Immutable
data class OrderScreenState(
    val recommendationList: List<RecommendationResponse>,
)
