package cl.optisoft.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onLoading
import cl.optisoft.common.response.onSuccess
import cl.optisoft.common.states.ScreenState
import cl.optisoft.network.response.NetworkErrors
import cl.optisoft.order.data.model.RecommendationItem
import cl.optisoft.order.presentation.OrderViewModel
import cl.optisoft.order.presentation.state.OrderScreenState
import cl.optisoft.order.ui.components.ChipItem
import cl.optisoft.order.ui.components.OpticalDotsLoading
import cl.optisoft.order.ui.components.SectionCard
import cl.optisoft.order.ui.components.ToggleRow
import cl.optisoft.order.ui.components.TwoEyeInputs
import kotlinx.coroutines.flow.StateFlow
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun OrderScreenView(
    viewModel: OrderViewModel = koinViewModel()
) {
    ContextOrder(
        uiState = viewModel.state,
        viewModel = viewModel
    )
}
@Composable
private fun ContextOrder(
    uiState: StateFlow<ScreenState<OrderScreenState, NetworkErrors>>,
    viewModel: OrderViewModel
) {
    val state by uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        state
            .onSuccess { screenState ->
                OrderCreateForm(
                    state = screenState,
                    onNameChange = viewModel::onNameChange,
                    onPhoneChange = viewModel::onPhoneChange,
                    onAddressChange = viewModel::onAddressChange,
                    onSphereLeftChange = viewModel::onSphereLeftChange,
                    onSphereRightChange = viewModel::onSphereRightChange,
                    onLeftAddChange = viewModel::onLeftAddChange,
                    onRightAddChange = viewModel::onRightAddChange,
                )
            }
            .onError {
                Text(text = "Error loading order")
            }
            .onLoading {
                OpticalDotsLoading()
            }
    }
}

@Composable
fun OrderCreateForm(
    state: OrderScreenState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onSphereLeftChange: (String) -> Unit,
    onSphereRightChange: (String) -> Unit,
    onLeftAddChange: (String) -> Unit,
    onRightAddChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CustomerInfoSection(
            state = state,
            onNameChange = onNameChange,
            onPhoneChange = onPhoneChange,
            onAddressChange = onAddressChange
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        PrescriptionSection(
            state = state,
            onSphereLeftChange = onSphereLeftChange,
            onSphereRightChange = onSphereRightChange,
            onLeftAddChange = onLeftAddChange,
            onRightAddChange = onRightAddChange,
        )

        RecommendationSection(
            recommendations = state.recommendationList
        )

        ActionButtonsSection(
            onCancel = { /* TODO */ },
            onCreate = { /* TODO */ }
        )
    }
}

@Composable
fun CustomerInfoSection(
    state: OrderScreenState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
) {
    SectionCard(title = "Información personal") {

        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChange,
            label = { Text("Nombre y apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.phone,
            onValueChange = onPhoneChange,
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.address,
            onValueChange = onAddressChange,
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PrescriptionSection(
    state: OrderScreenState,
    onSphereLeftChange: (String) -> Unit,
    onSphereRightChange: (String) -> Unit,
    onLeftAddChange: (String) -> Unit,
    onRightAddChange: (String) -> Unit,
) {
    SectionCard(title = "Esfera") {
        TwoEyeInputs(
            left = state.sphereLeft,
            right = state.sphereRight,
            addRight = state.addRight,
            addLeft = state.addLeft,
            onLeftSphereChange = onSphereLeftChange,
            onRightSphereChange = onSphereRightChange,
            onLeftAddChange = onLeftAddChange,
            onRightAddChange = onRightAddChange,
        )
    }

    Spacer(Modifier.height(4.dp))
    SectionCard(title = "Cilindro / Axis") {
        CylinderAxisRow(eyeLabel = "Right")
        CylinderAxisRow(eyeLabel = "Left")
    }

    Spacer(Modifier.height(4.dp))
    SectionCard(title = "") {
        ToggleRow("Prisma")
    }

    Spacer(Modifier.height(4.dp))
    SectionCard(title = "") {
        ToggleRow("Distancia Pupilar")
    }

}

@Composable
fun CylinderAxisRow(
    eyeLabel: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = eyeLabel,
            modifier = Modifier.width(50.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Cilindro") },
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Axis") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RecommendationSection(
    recommendations: List<RecommendationItem>
) {
    SectionCard(title = "Recomendaciones") {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            recommendations.forEach { item ->
                ChipItem(text = item.title)
            }
        }
    }
}

@Composable
fun ActionButtonsSection(
    onCancel: () -> Unit,
    onCreate: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onCancel,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(
                color = Color.White,
                text = "Cancelar Order"
            )
        }

        Button(
            onClick = onCreate,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                color = Color.White,
                text = "Crear Order"
            )
        }
    }
}


