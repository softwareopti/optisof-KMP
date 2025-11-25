package cl.optisoft.doctors.data

import cl.optisoft.common.response.Response
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.data.source.Factory
import cl.optisoft.network.response.NetworkErrors

internal class DataRepository (private val factory: Factory) {

    suspend fun getAllDoctors() : Response<List<DoctorResponse>, NetworkErrors> {
        return factory.getRemote().getAllDoctors()
    }
}