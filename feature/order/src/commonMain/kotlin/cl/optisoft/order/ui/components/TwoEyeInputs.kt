package cl.optisoft.order.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.input.KeyboardType
import cl.optisoft.order.ui.model.Eye
import cl.optisoft.order.ui.model.OpticalType
import cl.optisoft.order.ui.util.OpticalRanges
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TwoEyeInputs(
    first_title: String,
    second_title: String,
    eyeleft: String,
    eyeright: String,
    complRight: String,
    complLeft: String,
    onLeftEyeChange: (String) -> Unit,
    onRightEyeChange: (String) -> Unit,
    onLeftComplChange: (String) -> Unit,
    onRightComplChange: (String) -> Unit
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
            Text(first_title, modifier = Modifier.weight(1f))
            Text(second_title, modifier = Modifier.weight(1f))
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
                value = eyeright,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.RIGHT
                    activeType = OpticalType.SPHERE
                    showPicker = true
                }
            )

            OpticalPickerField(
                label = "Right",
                value = complRight,
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
                value = eyeleft,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.LEFT
                    activeType = OpticalType.SPHERE
                    showPicker = true
                }
            )

            OpticalPickerField(
                label = "Left",
                value = complLeft,
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
                else -> listOf()
            },
            zeroIndex = when (activeType) {
                OpticalType.SPHERE -> "0.00"
                OpticalType.ADD -> addValues.first()
                else -> "0"
            },
            onSelect = { value ->
                when (activeType) {
                    OpticalType.SPHERE -> {
                        when (activeEye) {
                            Eye.LEFT -> onLeftEyeChange(value)
                            Eye.RIGHT -> onRightEyeChange(value)
                        }
                    }

                    OpticalType.ADD,
                    OpticalType.AXIS -> {
                        when (activeEye) {
                            Eye.LEFT -> onLeftComplChange(value)
                            Eye.RIGHT -> onRightComplChange(value)
                        }
                    }
                }
                showPicker = false
            },
            onDismiss = { showPicker = false }
        )
    }
}


@Composable
fun TwoEyeInputsCilindro(
    first_title: String,
    second_title: String,
    eyeleft: String,
    eyeright: String,
    complRight: String,
    complLeft: String,
    onLeftEyeChange: (String) -> Unit,
    onRightEyeChange: (String) -> Unit,
    onLeftComplChange: (String) -> Unit,
    onRightComplChange: (String) -> Unit
) {
    val sphereValues = remember { OpticalRanges.sphere() }
    var activeEye by remember { mutableStateOf(Eye.LEFT) }
    var activeType by remember { mutableStateOf(OpticalType.SPHERE) }
    var showPicker by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.width(70.dp))
            Text(first_title, modifier = Modifier.weight(1f))
            Text(second_title, modifier = Modifier.weight(1f))
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
                value = eyeright,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.RIGHT
                    activeType = OpticalType.SPHERE
                    showPicker = true
                }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = complRight,
                onValueChange = { value ->
                    if (value.all { it.isDigit() }) {
                        val number = value.toIntOrNull()

                        if (number == null || number in 0..180) {
                            onRightComplChange(value)
                        }
                    }
                },
                label = { Text("Right") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
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
                value = eyeleft,
                modifier = Modifier.weight(1f),
                onClick = {
                    activeEye = Eye.LEFT
                    activeType = OpticalType.SPHERE
                    showPicker = true
                }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = complLeft,
                onValueChange = { value ->
                    if (value.all { it.isDigit() }) {
                        val number = value.toIntOrNull()

                        if (number == null || number in 0..180) {
                            onLeftComplChange(value)
                        }
                    }
                },
                label = { Text("Left") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
        }
        if (showPicker) {
            PickerDialog(
                values = when (activeType) {
                    OpticalType.SPHERE -> sphereValues
                    else -> listOf()
                },
                zeroIndex = when (activeType) {
                    OpticalType.SPHERE -> "0.00"
                    else -> "0"
                },
                onSelect = { value ->
                    when (activeType) {
                        OpticalType.SPHERE -> {
                            when (activeEye) {
                                Eye.LEFT -> onLeftEyeChange(value)
                                Eye.RIGHT -> onRightEyeChange(value)
                            }
                        }

                        OpticalType.ADD,
                        OpticalType.AXIS -> {
                            when (activeEye) {
                                Eye.LEFT -> onLeftComplChange(value)
                                Eye.RIGHT -> onRightComplChange(value)
                            }
                        }
                    }
                    showPicker = false
                },
                onDismiss = { showPicker = false }
            )
        }
    }
}
