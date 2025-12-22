package cl.optisoft.doctors.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import cl.optisoft.common.response.Response
import cl.optisoft.common.response.onSuccess
import cl.optisoft.doctors.presentation.DoctorSaveViewModel
import cl.optisoft.doctors.ui.components.DoctorDropdownField
import cl.optisoft.doctors.ui.components.DoctorFormHeader
import cl.optisoft.doctors.ui.components.DoctorPhotoSection
import cl.optisoft.doctors.ui.components.DoctorTextField
import cl.optisoft.doctors.ui.components.SaveButton
import cl.optisoft.doctors.ui.components.WheelDateTimePickerDialog
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@Composable
internal fun CreateDoctorScreenView(
    viewModel: DoctorSaveViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val birthday = remember { mutableStateOf(false) }
    var showPicker by remember { mutableStateOf(false) }

    uiState is Response.Success
    uiState.onSuccess { value ->
        Scaffold(
            topBar = {
                DoctorFormHeader(
                    isEditMode = value.isEditMode,
                    onBack = onBack
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
            ) {

                Spacer(Modifier.height(8.dp))

                if (showPicker) {
                    ImagePickerLauncher(
                        config = ImagePickerConfig(
                            onPhotoCaptured = { result ->
                                val bytes = result.loadBytes()
                                viewModel.onImagePicked(bytes)
                                showPicker = false
                            },
                            onDismiss = { showPicker = false },
                            onError = { showPicker = false }
                        )
                    )
                }

                // ----- FOTO -----
                DoctorPhotoSection(
                    photoBytes = value.image,
                    photoUrl = viewModel.photoUrl,
                    isEditMode = false,
                    onPickImage = { showPicker = true }
                )

                Spacer(Modifier.height(20.dp))

                // ----- CAMPOS -----
                DoctorTextField(
                    value = value.fullName,
                    onValueChange = viewModel::onFullNameChange,
                    placeholder = "Full Name",
                    leadingIcon = Icons.Default.Person,
                    isError = value.fullNameError
                )

                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = value.dateOfBirth,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (focusState.hasFocus) {
                                birthday.value = true
                            }
                        }, label = { Text("Fecha de nacimiento") }
                )

                WheelDateTimePickerDialog(birthday) {
                    viewModel.onDateOfBirthChange(it)
                }

                Spacer(Modifier.height(14.dp))

                DoctorTextField(
                    value = value.phone,
                    onValueChange = viewModel::onPhoneChange,
                    placeholder = "Phone",
                    leadingIcon = Icons.Default.Phone,
                    isError = value.phoneError
                )

                Spacer(Modifier.height(14.dp))

                DoctorTextField(
                    value = value.email,
                    onValueChange = viewModel::onEmailChange,
                    placeholder = "Email Address",
                    leadingIcon = Icons.Default.Email,
                    isError = value.emailError
                )

                Spacer(Modifier.height(14.dp))


                    DoctorDropdownField(
                        value = value.sucursal,
                        options = value.sucursalOptions,
                        onSelect = viewModel::onSucursalChange,
                        placeholder = "Sucursal",
                        isError = value.sucursalError != null
                    )



                Spacer(Modifier.height(30.dp))

                // ----- BOTÓN GUARDAR -----
                SaveButton(
                    enabled = value.canSave ,
                    onClick = viewModel::save
                )
            }
        }
    }


}


@Preview
@Composable
private fun Preview() {
    KoinPreview {
        CreateDoctorScreenView(onBack = {})
    }
}

@Composable
fun KoinPreview(content: @Composable () -> Unit) {
    KoinApplication(application = {
        modules(
            module {
                // Proveer mocks para el preview
                single { FakeRepository() }
                single { Dispatchers.Unconfined }
                viewModel { DoctorSaveViewModel(get(), get()) }
            }
        )
    }) {
        content()
    }
}

internal class FakeRepository



