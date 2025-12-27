package cl.optisoft.order.data.model

data class OrderModel(
    val idOrden: String = "",
    val idCompany: String = "",
    val commerceRut: String = "",
    val commerceName: String = "",
    val customerRut: String = "",
    val customerName: String = "",
    val customerAddress: String = "",
    val customerAge: String = "",
    val recipe: RecipeDraft = RecipeDraft(),
    val recommendations: RecommendationDraft = RecommendationDraft()
)

data class RecipeDraft(
    val prisma: Boolean = false,
    val rightHorizontalPrisma: String = "",
    val leftHorizontalPrisma: String = "",
    val rightVerticalPrisma: String = "",
    val leftVerticalPrisma: String = "",
    val pupillaryDistance: Boolean = false,
    val pdSimple: Boolean = false,
    val pdDouble: Boolean = false,
    val pdDifferentValue: Boolean = false,
    val dpSimpleWithoutDifferent: String = "",
    val dpDistanceSimpleWithDifferent: String = "",
    val dpNearSimpleWitDifferent: String = "",
    val dpRightWithoutDifferentDouble: String = "",
    val dpLeftWithoutDifferentDouble: String = "",
    val dpRightSphereWithDifferentDouble: String = "",
    val dpLeftSphereWithDifferentDouble: String = "",
    val dpAddWithDifferentDouble: String = ""
)

data class RecommendationDraft(
    val recommendation: List<RecommendationItem> = emptyList()
)

data class RecommendationItem(
    val id: String,
    val title: String
)