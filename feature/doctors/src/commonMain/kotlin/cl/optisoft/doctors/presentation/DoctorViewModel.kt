package cl.optisoft.doctors.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.optisoft.common.response.Response
import cl.optisoft.common.states.ScreenState
import cl.optisoft.common.response.combineResponses
import cl.optisoft.doctors.data.DataRepository
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.presentation.state.DoctorScreenState
import cl.optisoft.network.response.NetworkErrors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class DoctorViewModel(
    private val repository: DataRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state =
        MutableStateFlow<ScreenState<DoctorScreenState, NetworkErrors>>(Response.Idle)
    val state: StateFlow<ScreenState<DoctorScreenState, NetworkErrors>> = _state


    init {
        fetchAllDoctors()
    }

    private fun fetchAllDoctors() {
        viewModelScope.launch(coroutineDispatcher) {
            _state.value = Response.Loading


            val result = async { repository.getAllDoctors() }.await()


            _state.value = combineResponses(result) { article ->
                DoctorScreenState(article)
            }
        }
    }

    // ---------------------------------------------------------
    // ELIMINAR DOCTOR (Swipe to Delete o desde el menú)
    // ---------------------------------------------------------
    fun deleteDoctor(id: String) {
//        viewModelScope.launch(coroutineDispatcher) {
//
//            // Ejecutar eliminación en el repo
//            val result = repository.deleteDoctor(id)
//
//            // Si la eliminación fue exitosa, recargar lista
//            result.onSuccess {
//                fetchAllDoctors()
//            }.onFailure { error ->
//                _state.value = Response.Error(NetworkErrors.UnknownError(error.message))
//            }
//        }
    }

    // ---------------------------------------------------------
    // EDITAR DOCTOR (desde el menú de long-press)
    // ---------------------------------------------------------
    fun editDoctor(doctor: DoctorResponse) {
        // Aquí defines qué acción tomar:
        //   - Navegar a pantalla de edición
        //   - Guardar temporalmente en un estado
        //   - Enviar un evento
        // Ajusta según tu arquitectura

        // Ejemplo simple: guardar en un estado para navegación
//        _state.value = Response.Success(
//            (state.value.dataOrNull()?.copy(editingDoctor = doctor))
//                ?: DoctorScreenState(emptyList(), editingDoctor = doctor)
//        )
    }

}