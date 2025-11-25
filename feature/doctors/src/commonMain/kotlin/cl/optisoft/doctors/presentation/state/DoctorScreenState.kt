package cl.optisoft.doctors.presentation.state

import androidx.compose.runtime.Immutable
import cl.optisoft.doctors.data.model.DoctorResponse

@Immutable
data class DoctorScreenState(
    val doctorList: List<DoctorResponse>,
)
