package cl.optisoft.doctors.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.optisoft.common.response.Response
import cl.optisoft.common.response.onSuccess
import cl.optisoft.common.states.ScreenState
import cl.optisoft.doctors.data.DataRepository
import cl.optisoft.doctors.presentation.state.DoctorFormUiState
import cl.optisoft.network.response.NetworkErrors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DoctorSaveViewModel(
    private val repository: DataRepository,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state =
        MutableStateFlow<ScreenState<DoctorFormUiState, NetworkErrors>>(Response.Idle)
    val state: StateFlow<ScreenState<DoctorFormUiState, NetworkErrors>> = _state

    //    private val _uiState = MutableStateFlow(DoctorFormUiState())
//    val uiState: StateFlow<DoctorFormUiState> = _uiState
    var photoBytes by mutableStateOf<ByteArray?>(null)
        private set

    var photoUrl: String? by mutableStateOf(null)

    init {
        _state.value = Response.Success(DoctorFormUiState())
    }

    private inline fun updateFormState(
        update: (DoctorFormUiState) -> DoctorFormUiState
    ) {
        _state.update { current ->
            when (current) {
                is Response.Success -> {
                    val newUi = update(current.data)
                    val validatedUi = validateInternal(newUi)
                    Response.Success(validatedUi)
                }
                else -> current
            }
        }
    }

    private fun validateInternal(ui: DoctorFormUiState): DoctorFormUiState {
        val fullNameError =
            if (ui.fullName.isBlank()) "El nombre es obligatorio" else null

        val dateOfBirthError =
            if (ui.dateOfBirth.isBlank()) "La fecha de nacimiento es obligatoria" else null

        val phoneError =
            if (ui.phone.isBlank()) "El teléfono es obligatorio"
            else if (!ui.phone.matches(Regex("^[0-9+\\- ]{6,15}$")))
                "Formato de teléfono inválido"
            else null

        val emailError =
            if (ui.email.isBlank()) "El correo es obligatorio"
            else if (!ui.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")))
                "Correo electrónico inválido"
            else null

        val sucursalError =
            if (ui.sucursal.isBlank()) "Debes seleccionar una sucursal" else null

        val valid = listOf(
            fullNameError,
            dateOfBirthError,
            phoneError,
            emailError,
            sucursalError
        ).all { it == null }

        return ui.copy(
            fullNameError = fullNameError,
            dateOfBirthError = dateOfBirthError,
            phoneError = phoneError,
            emailError = emailError,
            sucursalError = sucursalError,
            canSave = valid
        )
    }
    fun onImagePicked(bytes: ByteArray) {
        updateFormState { it.copy(image = bytes) }
    }

    fun onImageSelected(url: String) {
        updateFormState { it.copy(photoUrl = url) }
    }

    fun onFullNameChange(v: String) {
        updateFormState { it.copy(fullName = v) }
    }

    fun onDateOfBirthChange(v: String) {
        updateFormState { it.copy(dateOfBirth = v) }
    }

    fun onPhoneChange(v: String) {
        updateFormState { it.copy(phone = v) }
    }

    fun onEmailChange(v: String) {
        updateFormState { it.copy(email = v) }
    }

    fun onSucursalChange(v: String) {
        updateFormState { it.copy(sucursal = v) }
    }

    // -------------------------------------------------------
    //  VALIDACIÓN
    // -------------------------------------------------------





    fun save() {
        val current = state.value

        // Validaciones previas
        if (current !is Response.Success) return
        if (!current.data.canSave) return
        if (current.data.isSaving) return

        val form = current.data

        _state.update {
            Response.Success(
                form.copy(
                    isSaving = true,
                    errorMessage = null
                )
            )
        }

        viewModelScope.launch(dispatcher) {

//            state.update { it.copy(isSaving = true, errorMessage = null) }

//            val response = if (states.isEditMode) {
            /*   repository.updateDoctor(
                   id = state.doctorId!!,
                   name = state.fullName,
                   birthDate = state.dateOfBirth,
                   phone = state.phone,
                   email = state.email,
                   sucursal = state.sucursal,
                   imageUrl = state.photoUrl
               )*/
//            } else {
            /* repository.createDoctor(
                 name = state.fullName,
                 birthDate = state.dateOfBirth,
                 phone = state.phone,
                 email = state.email,
                 idSucursal = state.sucursal,
                 image = state.image,
                 idOptica = "",
                 idUsuario = ""

             )*/
//            }
            /*
                        if (response.isSuccess) {
                            _uiState.update { it.copy(isSaving = false) }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isSaving = false,
                                    errorMessage = response.errorMessage ?: "Error guardando"
                                )
                            }
                        }*/
        }
    }
}