package cl.optisoft.doctors.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.optisoft.common.response.Response
import cl.optisoft.common.states.ScreenState
import cl.optisoft.common.response.combineResponses
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onSuccess
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

    var isEditing by mutableStateOf(false)
        private set

    var selectedIds by mutableStateOf(setOf<String>())
        private set

    init {
        fetchAllDoctors()
    }


    fun toggleEdit() {
        if (isEditing) {
            // exit edit: clear selections
            selectedIds = emptySet()
        }
        isEditing = !isEditing
    }

    fun toggleSelect(id: String) {
        selectedIds = if (id in selectedIds) selectedIds - id else selectedIds + id
    }

    fun clearSelection() {
        selectedIds = emptySet()
    }

    fun removeSelected() {
        if (selectedIds.isEmpty()) return
        viewModelScope.launch(coroutineDispatcher) {
            _state.value = Response.Loading

            // delete sequentially (could be parallel)
            selectedIds.forEach { id ->
                runCatching { repository.deleteDoctorById(id) }
            }
            selectedIds = emptySet()
            isEditing = false
            fetchAllDoctors()
        }
    }

    // ---------------- Reorder items in-memory ----------------
    fun moveItem(from: Int, to: Int) {
        val currentList = (_state.value as? Response.Success<DoctorScreenState>)?.data?.doctorList ?: return
        if (from == to) return
        val mutable = currentList.toMutableList()
        if (from !in mutable.indices) return
        val item = mutable.removeAt(from)
        val insertIndex = to.coerceIn(0, mutable.size)
        mutable.add(insertIndex, item)
        _state.value = Response.Success(DoctorScreenState(mutable))
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


    fun refreshDoctors() {
        fetchAllDoctors()
    }
    // ---------------------------------------------------------
    // ELIMINAR DOCTOR (Swipe to Delete o desde el menú)
    // ---------------------------------------------------------
    fun deleteDoctor(id: String) {
        viewModelScope.launch(coroutineDispatcher) {

            // Ejecutar eliminación en el repo
            val result = repository.deleteDoctorById(id)

            // Si la eliminación fue exitosa, recargar lista
            result.onSuccess {
                fetchAllDoctors()
            }.onError { error ->
                _state.value = Response.Error(error)
            }
        }
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