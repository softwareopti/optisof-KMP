package cl.optisoft.order.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoEyeInputs(
    label: String,
    left: String,
    right: String,
    onLeftChange: (String) -> Unit,
    onRightChange: (String) -> Unit
) {
    Column {
        Text(label)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = left,
                onValueChange = onLeftChange,
                label = { Text("Left") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = right,
                onValueChange = onRightChange,
                label = { Text("Right") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}