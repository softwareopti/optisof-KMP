package cl.optisoft.doctors.presentation.state

data class DoctorFormUiState(
    val isEditMode: Boolean = false,

    val doctorId: String? = null,

    var photoUrl: String? = null,
    var fullName: String = "",
    var dateOfBirth: String = "",
    var errorDateOfBirth: String = "",
    var phone: String = "",
    var email: String = "",
    var sucursal: String = "",
    var image: ByteArray? = null,
    val sucursalOptions: List<String> = emptyList(),
    val canSave: Boolean = false,
    val isSaving: Boolean = false,

    val fullNameError: String? = null,
    val dateOfBirthError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val sucursalError: String? = null,


    val errorMessage: String? = null
)
