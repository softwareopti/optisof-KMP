package cl.optisoft.doctors.data.model

data class DoctorRequest(
    val birdthdate: String,
    val idOptica: String,
    val idSucursal: String,
    val idUsuarioCreator: String,
    val mail: String,
    val name: String,
    val phone: String,
    val status: Boolean
)