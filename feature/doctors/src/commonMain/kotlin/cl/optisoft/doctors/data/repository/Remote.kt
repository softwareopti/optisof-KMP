package cl.optisoft.doctors.data.repository

import cl.optisoft.common.response.Response
import cl.optisoft.doctors.data.model.DoctorRequest
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.data.model.BranchesResponse
import cl.optisoft.network.response.NetworkErrors

internal interface Remote {

    suspend fun getAllBranches(): Response<List<BranchesResponse>, NetworkErrors>
    suspend fun getAllDoctors() : Response<List<DoctorResponse>, NetworkErrors>

    suspend fun saveDoctors(request: DoctorRequest) : Response<Any, NetworkErrors>

    suspend fun deleteDoctorById(id: String) : Response<Any, NetworkErrors>

    suspend fun updateDoctor(idDoctor: String, request: DoctorRequest) : Response<Any, NetworkErrors>

    suspend fun uploadImage(fileBytes: ByteArray, fileName: String): Response<Any, NetworkErrors>
}