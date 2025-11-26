package cl.optisoft.order.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.optisoft.order.presentation.OrderViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun OrderScreenView(
    viewModel: OrderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
}

