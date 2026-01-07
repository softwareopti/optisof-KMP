package cl.optisoft.order.presentation.state

import androidx.compose.runtime.Immutable
import cl.optisoft.order.data.model.OrderModel
import cl.optisoft.order.data.model.RecommendationItem

@Immutable
data class OrderScreenState(
    val recommendationList: List<RecommendationItem> = emptyList(),
    val order: OrderModel = OrderModel(),
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    val sphereLeft: String = "",
    val sphereRight: String = "",
    val addRight: String = "",
    val addLeft: String = "",
)
