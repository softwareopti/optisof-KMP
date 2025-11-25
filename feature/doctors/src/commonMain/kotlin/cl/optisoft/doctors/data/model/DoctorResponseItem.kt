package cl.optisoft.doctors.data.model

data class DoctorResponseItem(
    val _id: String,
    val idDoctor: String,
    val idOptica: String,
    val idSucursal: String,
    val mail: String,
    val name: String,
    val phone: String,
    val status: Boolean
)