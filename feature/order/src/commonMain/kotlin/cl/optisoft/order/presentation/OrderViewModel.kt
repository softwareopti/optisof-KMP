package cl.optisoft.order.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.optisoft.common.response.Response
import cl.optisoft.common.response.combineResponses
import cl.optisoft.common.states.ScreenState
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.DataRepository
import cl.optisoft.order.data.model.RecommendationItem
import cl.optisoft.order.presentation.state.OrderScreenState
import kotlinx.coroutines.CoroutineDispatcher
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
        _state.value = Response.Success(OrderScreenState())
        fetchAllRecommendations()
    }
    fun onNameChange(value: String) {
        updateState { it.copy(name = value) }
    }

    fun onPhoneChange(value: String) {
        updateState { it.copy(phone = value) }
    }

    fun onAddressChange(value: String) {
        updateState { it.copy(address = value) }
    }

    fun onSphereLeftChange(value: String) {
        updateState { it.copy(sphereLeft = value) }
    }

    fun onSphereRightChange(value: String) {
        updateState { it.copy(sphereRight = value) }
    }

    fun onLeftAddChange(value: String) {
        updateState { it.copy(addLeft = value) }
    }

    fun onRightAddChange(value: String) {
        updateState { it.copy(addRight = value) }
    }

    fun onLeftAxisChange(value: String) {
        updateState { it.copy(axisLeft = value) }
    }

    fun onRightAxisChange(value: String) {
        updateState { it.copy(axisRight = value) }
    }
    fun onCilindroLeftChange(value: String) {
        updateState { it.copy(cilindroLeft = value) }
    }

    fun onCilindroRightChange(value: String) {
        updateState { it.copy(cilindroRight = value) }
    }

    private fun updateState(
        reducer: (OrderScreenState) -> OrderScreenState
    ) {
        val current = _state.value
        if (current is Response.Success) {
            _state.value = Response.Success(reducer(current.data))
        }
    }


    private fun fetchAllRecommendations() {
        viewModelScope.launch(coroutineDispatcher) {
            _state.value = Response.Loading

            val result = repository.getAllRecommendations()

            _state.value = combineResponses(result) { recommendations ->
                OrderScreenState(
                    recommendationList = recommendations.map {
                        RecommendationItem(
                            id = it.id,
                            title = it.title
                        )
                    }
                )
            }
        }
    }

}