package cl.optisoft.common.navigation


sealed class DestinationRoutes(val route: String) {
    data object DoctorListScreen : DestinationRoutes(NavigationScreens.DOCTOR_LIST_SCREEN)

    data object CreateDoctorScreen : DestinationRoutes(NavigationScreens.DOCTOR_CREATE_SCREEN)

//    data object LoadingScreen : DestinationRoutes("${cl.multicaja.commons.NavigationScreens.LOADING}/{typeCard}") {
//        fun arguments(typeCard: String) = "${cl.multicaja.commons.NavigationScreens.LOADING}/$typeCard"
//    }
}


object NavigationScreens {
    const val DOCTOR_LIST_SCREEN = "doctor_list_screen"
    const val DOCTOR_CREATE_SCREEN = "doctor_create_screen"
}