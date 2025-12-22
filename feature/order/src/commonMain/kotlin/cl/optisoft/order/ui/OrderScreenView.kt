package cl.optisoft.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onLoading
import cl.optisoft.common.response.onSuccess
import cl.optisoft.order.presentation.OrderViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun OrderScreenView(
    viewModel: OrderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        state
            .onSuccess { recomen ->
                Text(text = recomen.recommendationList.last().title)
            }
            .onError { Text(text = "error") }
            .onLoading { CircularProgressIndicator() }
    }

}

