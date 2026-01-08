package cl.optisoft.order.presentation.state

import androidx.compose.runtime.Immutable
import cl.optisoft.order.data.model.OrderModel
import cl.optisoft.order.data.model.RecommendationItem

@Immutable
data class OrderScreenState(
    val recommendationList: List<RecommendationItem> = emptyList(),
    val order: OrderModel = OrderModel(),
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val sphereLeft: String = "",
    val sphereRight: String = "",
    val addRight: String = "",
    val addLeft: String = "",
    val cilindroLeft: String = "",
    val cilindroRight: String = "",
    val axisRight: String = "",
    val axisLeft: String = "",
)
