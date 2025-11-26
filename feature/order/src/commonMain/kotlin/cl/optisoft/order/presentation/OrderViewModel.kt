package cl.optisoft.order.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.optisoft.common.response.Response
import cl.optisoft.common.response.combineResponses
import cl.optisoft.common.states.ScreenState
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.DataRepository
import cl.optisoft.order.presentation.state.OrderScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class OrderViewModel(
    private val repository: DataRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _state =
        MutableStateFlow<ScreenState<OrderScreenState, NetworkErrors>>(Response.Idle)
    val state: StateFlow<ScreenState<OrderScreenState, NetworkErrors>> = _state

    init {
        fetchAllRecommendations()
    }

    private fun fetchAllRecommendations() {
        viewModelScope.launch(coroutineDispatcher) {
            _state.value = Response.Loading


            val result = async { repository.getAllRecommendations() }.await()


            _state.value = combineResponses(result) { recommendation ->
                OrderScreenState(recommendation)
            }
        }
    }

}