package cl.optisoft.doctors.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissValue
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cl.optisoft.common.navigation.DestinationRoutes
import cl.optisoft.common.response.Response
import cl.optisoft.common.response.onError
import cl.optisoft.common.response.onLoading
import cl.optisoft.common.response.onSuccess
import cl.optisoft.doctors.data.model.DoctorResponse
import cl.optisoft.doctors.presentation.DoctorViewModel
import cl.optisoft.doctors.presentation.state.DoctorScreenState
import optisoft.designsystem.generated.resources.Res
import optisoft.designsystem.generated.resources.medical
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun DoctorScreenView(
    viewModel: DoctorViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isRefreshing = state is Response.Loading

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshDoctors() }
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pullRefresh(pullRefreshState)
    ) {

        state
            .onSuccess { doctor ->

                val CustomMint = Color(0xFFB0EED9)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {

                        DoctorsHeader(
                            isEditing = viewModel.isEditing,
                            selectedCount = viewModel.selectedIds.size,
                            onEditClick = { viewModel.toggleEdit() },
                            onRemoveClick = { viewModel.removeSelected() },
                            onBack = { /* tu navegación */ }
                        )
                        /*TopAppBar(
                            title = { Text("Lista de Doctores") },
                            modifier = Modifier.fillMaxWidth(),
                            navigationIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = null
                                )
                            },
                            actions = {
                                if (viewModel.isEditing) {
                                    Text(
                                        "Remove",
                                        color = Color.Red,
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp)
                                            .clickable { viewModel.removeSelected() }
                                    )
                                }

                                Text(
                                    if (viewModel.isEditing) "Done" else "Edit",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp)
                                        .clickable { viewModel.toggleEdit() }
                                )
                            }
                        )*/
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = FloatingActionButtonDefaults.largeShape,
                            containerColor = CustomMint,
                            onClick = {
                                navController.navigate(DestinationRoutes.CreateDoctorScreen.route)
                            }
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
                            .padding(horizontal = 16.dp)
                    ) {
                        items(
                            items = doctor.doctorList,
                            key = { it.id }   // ⚠️ IMPORTANTE: clave estable para animar + reordenar
                        ) { item ->
                            val itemHeight = 80.dp
                            DoctorRowEditable(
                                item = item,
                                viewModel = viewModel,
                                itemHeightDp = itemHeight
                            )

                        /*    var showPopup by remember { mutableStateOf(false) }
                            var itemPosition by remember { mutableStateOf(0f to 0f) }

                            var itemSize by remember { mutableStateOf(0 to 0) }

                            val density = LocalDensity.current

                            val dismissState = rememberDismissState(
                                confirmStateChange = { value ->
                                    if (value == DismissValue.DismissedToStart ||
                                        value == DismissValue.DismissedToEnd
                                    ) {
                                        viewModel.deleteDoctor(item.id)
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
                                        androidx.compose.material.DismissDirection.EndToStart -> Color(
                                            0xFFFFEBEB
                                        )

                                        null -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(horizontal = 20.dp),
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
                                            .combinedClickable(
                                                onClick = {},
                                                onLongClick = { showPopup = true }
                                            )
                                            .onGloballyPositioned { layoutCoordinates ->
                                                val windowPos =
                                                    layoutCoordinates.localToWindow(Offset.Zero)
                                                itemPosition = windowPos.x to windowPos.y

                                                val size = layoutCoordinates.size
                                                itemSize = size.width to size.height
                                            }
                                    )



                                }
                            )*/

                            // ---------- POPUP encima del item ----------
                          /*  if (showPopup) {

                                Popup(
                                    alignment = Alignment.TopStart,
                                    offset = IntOffset(
                                        x = itemPosition.first.toInt(),
                                        y = (itemPosition.second - (itemSize.second * 0.6f)).toInt()
                                    ),
                                    onDismissRequest = { showPopup = false }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(Color.White)
                                            .padding(12.dp)
                                            .width(150.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            "Editar",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    showPopup = false
                                                    viewModel.editDoctor(item)
                                                }
                                                .padding(8.dp)
                                        )

                                        Text(
                                            "Eliminar",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    showPopup = false
                                                    viewModel.deleteDoctor(item.id)
                                                }
                                                .padding(8.dp)
                                        )
                                    }
                                }


                            }*/
                        }

                    }
                }
            }
            .onLoading { LoadingContent() }
            .onError { /* manejar error */ }

        /*PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )*/
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DoctorRowEditable(
    item: DoctorResponse,
    viewModel: DoctorViewModel,
    itemHeightDp: Dp
) {
    var showPopup by remember { mutableStateOf(false) }
    var itemPosition by remember { mutableStateOf(Offset.Zero) }
    var itemSize by remember { mutableStateOf(0 to 0) }
    val density = LocalDensity.current

    val dismissState = rememberDismissState(
        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart ||
                value == DismissValue.DismissedToEnd
            ) {
                viewModel.deleteDoctor(item.id)
                true
            } else false
        }
    )

    // Reorder accumulated drag
    var accumulated by remember { mutableStateOf(0f) }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(
            androidx.compose.material.DismissDirection.EndToStart,
            androidx.compose.material.DismissDirection.StartToEnd
        ),
        background = {
            val color = when (dismissState.dismissDirection) {
                androidx.compose.material.DismissDirection.StartToEnd,
                androidx.compose.material.DismissDirection.EndToStart -> Color(0xFFFFEBEB)
                null -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
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
          val density =  LocalDensity.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .onGloballyPositioned { coords ->
                        itemPosition = coords.localToWindow(Offset.Zero)
                        val size = coords.size
                        itemSize = size.width to size.height
                    }
                    .combinedClickable(
                        onClick = {},
                        onLongClick = { showPopup = true }
                    )
                    .then(
                        if (viewModel.isEditing) {
                            Modifier.pointerInput(item.id) {
                                val itemHeightPx = with(density) { itemHeightDp.toPx() }

                                detectDragGestures(
                                    onDragStart = {
                                        accumulated = 0f
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        accumulated += dragAmount.y

                                        val threshold = itemHeightPx * 0.5f // 50% del item

                                        if (abs(accumulated) >= threshold) {

                                            val currentList =
                                                (viewModel.state.value as? Response.Success<DoctorScreenState>)
                                                    ?.data?.doctorList ?: return@detectDragGestures

                                            val currentIndex = currentList.indexOfFirst { it.id == item.id }
                                            if (currentIndex == -1) return@detectDragGestures

                                            val delta = if (accumulated > 0) 1 else -1
                                            val targetIndex = (currentIndex + delta)
                                                .coerceIn(0, currentList.size - 1)

                                            if (targetIndex != currentIndex) {
                                                viewModel.moveItem(currentIndex, targetIndex)
                                                accumulated = 0f
                                            }
                                        }
                                    },
                                    onDragEnd = {
                                        accumulated = 0f
                                    },
                                    onDragCancel = {
                                        accumulated = 0f
                                    }
                                )
                            }
                        } else Modifier
                    )
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White)
                    .padding(20.dp)
            ) {

                // Checkbox al editar
                if (viewModel.isEditing) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable { viewModel.toggleSelect(item.id) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.id in viewModel.selectedIds) {
                            Box(
                                Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF00C48C))
                            )
                        } else {
                            Box(
                                Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color(0xFFBDBDBD), CircleShape)
                            )
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                }

                // Icon — funciona en iOS + Android
                Image(
                    painter = painterResource(Res.drawable.medical),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.idSucursal, color = Color(0xFF9BA1A2))
                    Text(text = item.name, color = Color(0xFF2B2F30))
                }

                Spacer(Modifier.width(8.dp))

                // Handle de reordenar
                if (viewModel.isEditing) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "drag",
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color(0xFFD0D6D4),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Popup contextual
            if (showPopup && !viewModel.isEditing) {
                Popup(
                    alignment = Alignment.TopStart,
                    offset = IntOffset(
                        itemPosition.x.toInt(),
                        (itemPosition.y - itemSize.second * 0.6f).toInt()
                    ),
                    onDismissRequest = { showPopup = false }
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White)
                            .padding(12.dp)
                            .width(150.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Editar",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showPopup = false
//                                    viewModel.enterEditMode()
                                }
                                .padding(8.dp)
                        )
                        Text(
                            "Eliminar",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showPopup = false
                                    viewModel.deleteDoctor(item.id)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DoctorsHeader(
    isEditing: Boolean,
    selectedCount: Int,
    onEditClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Top)
                    .asPaddingValues()
            )
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {

        // --- Top bar (Back + Title + Search) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onBack() }
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Medicos",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        // --- Subtítulo "Listado Médicos 👩‍⚕️" ---
        Text(
            "Listado Medicos 👩‍⚕️",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF2B2F30)
            )
        )

        Spacer(Modifier.height(6.dp))

        // --- Remove / Edit row ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Remove (solo visible si está editando)
            if (isEditing) {
                Text(
                    text = if (selectedCount > 0)
                        "Remove ($selectedCount)"
                    else
                        "Remove",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFFD9534F)
                    ),
                    modifier = Modifier.clickable { onRemoveClick() }
                )
            } else {
                Spacer(Modifier.width(1.dp))
            }

            // Edit
            Text(
                text = if (isEditing) "Done" else "Edit",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF2B2F30)
                ),
                modifier = Modifier.clickable { onEditClick() }
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}


@Composable
fun LoadingContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
