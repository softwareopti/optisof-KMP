package cl.optisoft.doctors.data.repository

import cl.optisoft.common.response.Response
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.network.response.NetworkErrors

internal interface Remote {
    suspend fun getAllDoctors() : Response<List<DoctorResponse>, NetworkErrors>
}