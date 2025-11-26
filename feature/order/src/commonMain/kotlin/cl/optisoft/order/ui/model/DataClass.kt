package cl.optisoft.order.ui.model

data class Diagnostic(
    val astigmatism: Boolean = false,
    val myopia: Boolean = false,
    val prebyopia: Boolean = false,
    val farsightedness: Boolean = false
)

data class DetailRecipe(
    val idRecipe: String = "",
    val sphereRight: String = "",
    val sphereLeft: String = "",
    val addSphere: String = "",
    val astigRightCylinder: String = "",
    val astigLeftCylinder: String = "",
    val astigRightEje: String = "",
    val astigLeftEje: String = "",
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

data class RecommendationDetail(
    val recommendation: List<String> = emptyList()
)

data class Recipe(
    val recommendations: RecommendationDetail = RecommendationDetail(),
    val details: DetailRecipe = DetailRecipe(),
    val diagnostic: Diagnostic = Diagnostic(),
    val notes: String = "",
    val externalRecipe: Boolean = false,
    val externalRecipeBase64: String = "",
    val timestampRecipe: String = "",
    val statusRecipe: String = "INICIADA"
)

data class Order(
    val idOrden: String = "",
    val recipe: Recipe = Recipe()
)
