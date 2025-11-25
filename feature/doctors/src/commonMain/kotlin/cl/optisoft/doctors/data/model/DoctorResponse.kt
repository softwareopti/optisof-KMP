package cl.optisoft.doctors.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DoctorResponse (
    @SerialName("_id") val id:String,
    val idDoctor: String,
    val idOptica: String,
    val idSucursal: String,
    val mail: String,
    val name: String,
    val phone: String,
    val status: Boolean
)