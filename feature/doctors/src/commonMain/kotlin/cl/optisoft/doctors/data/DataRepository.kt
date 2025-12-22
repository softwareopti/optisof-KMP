package cl.optisoft.doctors.data

import cl.optisoft.common.response.Response
import cl.optisoft.doctors.data.model.DoctorRequest
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.data.source.Factory
import cl.optisoft.network.response.NetworkErrors
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class DataRepository(private val factory: Factory) {

    suspend fun getAllDoctors(): Response<List<DoctorResponse>, NetworkErrors> {
        return factory.getRemote().getAllDoctors()
    }


    suspend fun deleteDoctorById(id: String): Response<Any, NetworkErrors> {
        return factory.getRemote().deleteDoctorById(id)
    }

    suspend fun createDoctor(
        name: String,
        birthDate: String,
        phone: String,
        email: String,
        idSucursal: String,
        idOptica: String,
        idUsuario: String,
        image: ByteArray?
    ): Response<Any, NetworkErrors> {
        return coroutineScope {

            async {
                factory.getRemote().saveDoctors(
                    DoctorRequest(
                        birdthdate = birthDate,
                        idOptica = idOptica,
                        idSucursal = idSucursal,
                        idUsuarioCreator = idUsuario,
                        mail = email,
                        name = name,
                        phone = phone,
                        status = true
                    )
                )
            }.await()

            async { factory.getRemote().uploadImage(image!!, name) }.await()
        }


    }

    /* suspend fun updateDoctor(re) : Response<Any, NetworkErrors>{

     }*/

}