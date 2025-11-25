package cl.optisoft.doctors.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onLoading
import cl.optisoft.common.response.onSuccess
import cl.optisoft.doctors.presentation.DoctorViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun DoctorScreenView(
    viewModel: DoctorViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        state
            .onSuccess {
//                ArticleDetail(it, onBackPress)

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(it.doctorList) { text ->
                        SimpleListItem(text.name)
                    }
                }
            }
            .onLoading { LoadingContent() }
            .onError {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    PagingErrorButton(onRetry = viewModel::retry)
//                }
            }
    }
}

@Composable
fun SimpleListItem(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { println("Click en: $text") }
            .padding(16.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
        Divider()
    }
}

@Composable
fun LoadingContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}