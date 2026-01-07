package cl.optisoft.order.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.VerticalAlignmentLine
import cl.optisoft.order.ui.model.Eye
import cl.optisoft.order.ui.model.OpticalType
import cl.optisoft.order.ui.util.OpticalRanges
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TwoEyeInputs(
    left: String,
    right: String,
    addRight: String,
    addLeft: String,
    onLeftSphereChange: (String) -> Unit,
    onRightSphereChange: (String) -> Unit,
    onLeftAddChange: (String) -> Unit,
    onRightAddChange: (String) -> Unit
) {
    val sphereValues = remember { OpticalRanges.sphere() }
    val addValues = remember { OpticalRanges.add() }
    var activeEye by remember { mutableStateOf(Eye.LEFT) }
    var activeType by remember { mutableStateOf(OpticalType.SPHERE) }
    var showPicker by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            Text("Esfera", modifier = Modifier.weight(1f))
            Text("Near add", modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Right",
                modifier = Modifier
                    .padding(start = 2.dp, end = 10.dp)
                    .width(50.dp).align(Alignment.CenterVertically)
            )
            OpticalPickerField(
                label = "Right",
                value = right,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.RIGHT
                    activeType = OpticalType.SPHERE
                    showPicker = true
                }
            )

            OpticalPickerField(
                label = "Near right",
                value = addRight,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.RIGHT
                    activeType = OpticalType.ADD
                    showPicker = true
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Left",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(60.dp).align(Alignment.CenterVertically)
            )
            OpticalPickerField(
                label = "Left",
                value = left,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.LEFT
                    activeType = OpticalType.SPHERE
                    showPicker = true
                }
            )

            OpticalPickerField(
                label = "Near Left",
                value = addLeft,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.LEFT
                    activeType = OpticalType.ADD
                    showPicker = true
                }
            )
        }
    }

    if (showPicker) {
        PickerDialog(
            values = when (activeType) {
                OpticalType.SPHERE -> sphereValues
                OpticalType.ADD -> addValues
            },
            zeroIndex = when (activeType) {
                OpticalType.SPHERE -> "0.00"
                OpticalType.ADD -> addValues.first()
            },
            onSelect = { value ->
                when (activeType) {
                    OpticalType.SPHERE -> {
                        when (activeEye) {
                            Eye.LEFT -> onLeftSphereChange(value)
                            Eye.RIGHT -> onRightSphereChange(value)
                        }
                    }

                    OpticalType.ADD -> {
                        when (activeEye) {
                            Eye.LEFT -> onLeftAddChange(value)
                            Eye.RIGHT -> onRightAddChange(value)
                        }
                    }
                }
                showPicker = false
            },
            onDismiss = { showPicker = false }
        )
    }
}
