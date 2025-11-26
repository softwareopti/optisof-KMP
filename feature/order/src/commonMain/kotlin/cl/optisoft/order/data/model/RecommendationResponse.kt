package cl.optisoft.order.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationResponse(
    @SerialName("_id") val id: String,
    val title: String,
    val description: String,
    val status: Boolean,
    val createdAt: String,
    val updatedAt: String
)