package cl.optisoft.doctors.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onLoading
import cl.optisoft.common.response.onSuccess
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.presentation.DoctorViewModel
import optisoft.designsystem.generated.resources.Res
import optisoft.designsystem.generated.resources.medical
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun DoctorScreenView(
    viewModel: DoctorViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        state
            .onSuccess { doctor ->

                val CustomMint = Color(0xFFB0EED9)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Lista de Doctores") },
                            modifier = Modifier.fillMaxWidth(),
                            navigationIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = FloatingActionButtonDefaults.largeShape,
                            containerColor = CustomMint,
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                ) { paddingValues ->

                /*    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(doctor.doctorList) { item ->
                            BranchItemCard(
                                branch = item.idSucursal,
                                name = item.name,
                                icon = painterResource(Res.drawable.medical),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }*/

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding( horizontal = 16.dp)
                    ) {
                        items(
                            items = doctor.doctorList,
                            key = { it.id } // clave estable para animaciones
                        ) { item ->

                            val dismissState = rememberDismissState(
                                confirmStateChange = { value ->
                                    if (value == DismissValue.DismissedToStart ||
                                        value == DismissValue.DismissedToEnd
                                    ) {
                                        viewModel.deleteDoctor(item.idSucursal)
                                        true
                                    } else false
                                }
                            )

                            SwipeToDismiss(
                                modifier = Modifier.wrapContentHeight(),
                                state = dismissState,
                                directions = setOf(
                                    androidx.compose.material.DismissDirection.EndToStart,
                                    androidx.compose.material.DismissDirection.StartToEnd
                                ),
                                background = {
                                    // Fondo rojo con icono de eliminar
                                    val color = when (dismissState.dismissDirection) {
                                        androidx.compose.material.DismissDirection.StartToEnd,
                                        androidx.compose.material.DismissDirection.EndToStart -> Color(0xFFFFEBEB)
                                        null -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(horizontal = 20.dp)
                                        ,
                                        contentAlignment = when (dismissState.dismissDirection) {
                                            androidx.compose.material.DismissDirection.StartToEnd -> Alignment.CenterStart
                                            androidx.compose.material.DismissDirection.EndToStart -> Alignment.CenterEnd
                                            null -> Alignment.CenterEnd
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = Color.Red
                                        )
                                    }
                                },
                                dismissContent = {
                                    BranchItemCard(
                                        branch = item.idSucursal,
                                        name = item.name,
                                        icon = painterResource(Res.drawable.medical),
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )

                                }
                            )
                        }
                    }

                }
            }
            .onLoading { LoadingContent() }
            .onError { /* manejar error */ }
    }
}

@Composable
fun BranchItemCard(
    branch: String,
    name: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF5F7F7)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column {
                    Text(
                        text = branch,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF9BA1A2)
                    )
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF2B2F30)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = Color(0xFFD0D6D4),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
