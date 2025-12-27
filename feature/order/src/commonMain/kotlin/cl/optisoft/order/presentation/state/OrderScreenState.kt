package cl.optisoft.order.presentation.state

import androidx.compose.runtime.Immutable
import cl.optisoft.order.data.model.OrderModel
import cl.optisoft.order.data.model.RecommendationItem

@Immutable
data class OrderScreenState(
    val recommendationList: List<RecommendationItem> = emptyList(),
    val order: OrderModel = OrderModel()
)
