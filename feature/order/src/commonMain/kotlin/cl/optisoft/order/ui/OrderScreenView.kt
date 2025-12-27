package cl.optisoft.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import cl.optisoft.order.ui.components.EyeInputRow
import cl.optisoft.order.ui.components.OpticalDotsLoading
import cl.optisoft.order.ui.components.SectionCard
import cl.optisoft.order.ui.components.ToggleRow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun OrderScreenView(
    viewModel: OrderViewModel = koinViewModel()
) {
    ContextOrder(
        uiState = viewModel.state,
    )
}

@Composable
fun ContextOrder(
    uiState: StateFlow<ScreenState<OrderScreenState, NetworkErrors>>
) {
    val state by uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        state
            .onSuccess { screenState ->
                OrderCreateForm(
                    state = screenState
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
    state: OrderScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CustomerInfoSection()

        PrescriptionSection()

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
fun CustomerInfoSection() {
    SectionCard(title = "Información personal") {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Nombre y apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PrescriptionSection() {
    SectionCard(title = "Prescripción") {

        Text("Esfera")
        EyeInputRow()

        Spacer(Modifier.height(8.dp))

        Text("Cilindro / Axis")
        CylinderAxisRow(eyeLabel = "Right")
        CylinderAxisRow(eyeLabel = "Left")

        ToggleRow("Prisma")
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

@Preview()
@Composable
fun PrescriptionPreview() {
    PrescriptionSection()
}


