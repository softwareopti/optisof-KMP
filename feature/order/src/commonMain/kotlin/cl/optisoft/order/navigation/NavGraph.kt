package cl.optisoft.order.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cl.optisoft.common.navigation.DestinationRoutes
import cl.optisoft.order.ui.OrderScreenView

fun NavGraphBuilder.orderNavGraph(navController: NavController) {

    composable(route = DestinationRoutes.OrderScreen.route) {
        OrderScreenView()
    }

}
