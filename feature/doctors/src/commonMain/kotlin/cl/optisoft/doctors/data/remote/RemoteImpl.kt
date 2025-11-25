package cl.optisoft.doctors.data.remote

import cl.optisoft.common.response.Response
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.data.repository.Remote
import cl.optisoft.network.call.SafeApiCall
import cl.optisoft.network.response.NetworkErrors
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class RemoteImpl(private val httpClient: HttpClient) : Remote {

    override suspend fun getAllDoctors(): Response<List<DoctorResponse>, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.get("/api/medicals/")
        }
    }
}