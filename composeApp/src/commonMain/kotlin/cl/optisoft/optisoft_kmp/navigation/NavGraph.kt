package cl.optisoft.optisoft_kmp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cl.optisoft.common.navigation.DestinationRoutes
import cl.optisoft.doctors.navigation.doctorNavGraph
import cl.optisoft.order.navigation.orderNavGraph

@Composable
fun Navigation(
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DestinationRoutes.OrderScreen.route) {
        doctorNavGraph(navController)
        orderNavGraph(navController)
    }
}