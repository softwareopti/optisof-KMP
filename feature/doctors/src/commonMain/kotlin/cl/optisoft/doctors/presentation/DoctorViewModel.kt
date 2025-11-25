package cl.optisoft.doctors.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.optisoft.common.response.Response
import cl.optisoft.common.states.ScreenState
import cl.optisoft.common.response.combineResponses
import cl.optisoft.doctors.data.DataRepository
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
}