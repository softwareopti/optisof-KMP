package cl.optisoft.doctors.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cl.optisoft.common.navigation.DestinationRoutes
import cl.optisoft.doctors.ui.CreateDoctorScreenView
import cl.optisoft.doctors.ui.DoctorScreenView

fun NavGraphBuilder.doctorNavGraph(navController: NavController) {

    composable(route = DestinationRoutes.DoctorListScreen.route) {
        DoctorScreenView(navController = navController)
    }

    composable(route = DestinationRoutes.CreateDoctorScreen.route) {
        CreateDoctorScreenView(onBack = {
            navController.popBackStack()
        })
    }
}
