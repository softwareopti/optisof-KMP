package cl.optisoft.doctors.data.remote

import cl.optisoft.common.response.Response
import cl.optisoft.doctors.data.model.DoctorRequest
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.data.model.BranchesResponse
import cl.optisoft.doctors.data.repository.Remote
import cl.optisoft.network.call.SafeApiCall
import cl.optisoft.network.response.NetworkErrors
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
//import kotlinx.atomicfu.TraceBase.None.append

internal class RemoteImpl(private val httpClient: HttpClient) : Remote {

    override suspend fun getAllBranches(): Response<List<BranchesResponse>, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.get("/api/sucursales/")
        }
    }

    override suspend fun getAllDoctors(): Response<List<DoctorResponse>, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.get("/api/medicals/")
        }
    }

    override suspend fun saveDoctors(request: DoctorRequest): Response<Any, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.post("/api/medicals/create"){
                setBody(request)
            }
        }
    }

    override suspend fun deleteDoctorById(id: String): Response<Any, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.delete("/api/medicals/delete/${id}")
        }
    }

    override suspend fun updateDoctor(idDoctor: String, request: DoctorRequest): Response<Any, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.put("/api/medicals/update/${idDoctor}"){
                setBody(request)
            }
        }
    }

    override suspend fun uploadImage(fileBytes: ByteArray, fileName: String): Response<Any, NetworkErrors> {
        return SafeApiCall.launch {
            httpClient.submitFormWithBinaryData(
                url = "/api/upload",
                formData = formData {
                    append("image", fileBytes, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=$fileName")
                    })
                }
            )

        }
    }
}