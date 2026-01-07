package cl.optisoft.order.ui.util

import kotlin.math.roundToInt

object OpticalRanges {

    fun sphere(): List<String> =
        generateRange(-12.0, 12.0, 0.25, withSign = true)

    fun cylinder(): List<String> =
        generateRange(-6.0, 0.0, 0.25, withSign = false)

    fun add(): List<String> =
        generateRange(0.75, 4.0, 0.25, withSign = true)

    fun axis(): List<String> =
        (0..180).map { it.toString() }

    private fun generateRange(
        min: Double,
        max: Double,
        step: Double,
        withSign: Boolean
    ): List<String> {
        val count = ((max - min) / step).roundToInt()

        return (0..count).map { i ->
            val raw = min + (i * step)
            formatOpticalValue(raw, withSign)
        }
    }

    private fun formatOpticalValue(
        value: Double,
        withSign: Boolean
    ): String {
        val rounded = (value * 100).roundToInt() / 100.0
        val absValue = kotlin.math.abs(rounded)

        val integer = absValue.toInt()
        val decimal = ((absValue - integer) * 100).roundToInt()

        val formatted = "$integer.${decimal.toString().padStart(2, '0')}"

        return when {
            withSign && rounded > 0 -> "+$formatted"
            rounded < 0 -> "-$formatted"
            else -> formatted
        }
    }
}