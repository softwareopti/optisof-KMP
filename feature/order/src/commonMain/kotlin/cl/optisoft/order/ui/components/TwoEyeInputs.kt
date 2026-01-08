package cl.optisoft.order.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.optisoft.order.ui.model.ComplementType
import cl.optisoft.order.ui.model.Eye
import cl.optisoft.order.ui.model.EyeOpticalType
import cl.optisoft.order.ui.util.OpticalRanges

@Composable
fun TwoEyeInputsBase(
    firstTitle: String,
    secondTitle: String,
    eyeType: EyeOpticalType,
    complementType: ComplementType,
    leftEye: String,
    rightEye: String,
    leftCompl: String,
    rightCompl: String,
    onLeftEyeChange: (String) -> Unit,
    onRightEyeChange: (String) -> Unit,
    onLeftComplChange: (String) -> Unit,
    onRightComplChange: (String) -> Unit
) {
    val sphereValues = remember { OpticalRanges.sphere() }
    val cylinderValues = remember { OpticalRanges.cylinder() }
    val addValues = remember { OpticalRanges.add() }

    var activeEye by remember { mutableStateOf<Eye?>(null) }
    var activeField by remember { mutableStateOf<Any?>(null) }
    var showPicker by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row {
            Spacer(Modifier.width(70.dp))
            Text(firstTitle, Modifier.weight(1f))
            Text(secondTitle, Modifier.weight(1f))
        }

        EyeRow(
            eyeLabel = "Right",
            eyeValue = rightEye,
            onEyeClick = {
                activeEye = Eye.RIGHT
                activeField = eyeType
                showPicker = true
            }
        ) { modifier ->
            ComplementField(
                complementType = complementType,
                value = rightCompl,
                modifier = modifier,
                onPickerClick = {
                    activeEye = Eye.RIGHT
                    activeField = complementType
                    showPicker = true
                },
                onValueChange = onRightComplChange
            )
        }

        EyeRow(
            eyeLabel = "Left",
            eyeValue = leftEye,
            onEyeClick = {
                activeEye = Eye.LEFT
                activeField = eyeType
                showPicker = true
            }
        ) { modifier ->
            ComplementField(
                complementType = complementType,
                value = leftCompl,
                modifier = modifier,
                onPickerClick = {
                    activeEye = Eye.LEFT
                    activeField = complementType
                    showPicker = true
                },
                onValueChange = onLeftComplChange
            )
        }
    }

    if (showPicker && activeEye != null && activeField != null) {
        PickerDialog(
            values = when (activeField) {
                EyeOpticalType.SPHERE -> sphereValues
                EyeOpticalType.CYLINDER -> cylinderValues
                ComplementType.ADD -> addValues
                else -> emptyList()
            },
            zeroIndex = when (activeField) {
                ComplementType.ADD -> addValues.first()
                else -> "0.00"
            },
            onSelect = { value ->
                when (activeField) {
                    is EyeOpticalType -> {
                        if (activeEye == Eye.LEFT) onLeftEyeChange(value)
                        else onRightEyeChange(value)
                    }

                    is ComplementType -> {
                        if (activeEye == Eye.LEFT) onLeftComplChange(value)
                        else onRightComplChange(value)
                    }
                }
                showPicker = false
            },
            onDismiss = { showPicker = false }
        )
    }
}

@Composable
private fun ComplementField(
    complementType: ComplementType,
    value: String,
    modifier: Modifier,
    onPickerClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    when (complementType) {
        ComplementType.AXIS ->
            AxisInputField(
                value = value,
                onChange = onValueChange,
                modifier = modifier
            )

        ComplementType.ADD ->
            OpticalPickerField(
                label = "Add",
                value = value,
                modifier = modifier,
                onClick = onPickerClick
            )
    }
}

@Composable
fun AxisInputField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = value,
        onValueChange = { input ->
            if (input.isEmpty()) {
                onChange("")
                return@TextField
            }

            val number = input.toIntOrNull()
            if (number != null && number in 0..180) {
                onChange(input)
            }
        },
        label = { Text("Axis") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        modifier = modifier
            .focusRequester(focusRequester)
            .clickable {
                focusRequester.requestFocus()
            }
    )
}

@Composable
private fun EyeRow(
    eyeLabel: String,
    eyeValue: String,
    onEyeClick: () -> Unit,
    complementField: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = eyeLabel,
            modifier = Modifier
                .width(60.dp)
                .align(Alignment.CenterVertically)
        )

        OpticalPickerField(
            label = eyeLabel,
            value = eyeValue,
            modifier = Modifier.weight(1f),
            onClick = onEyeClick
        )

        complementField(Modifier.weight(1f))
    }
}