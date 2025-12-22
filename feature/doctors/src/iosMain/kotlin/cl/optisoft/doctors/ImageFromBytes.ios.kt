package cl.optisoft.doctors

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toImageBitmap(): ImageBitmap? {
    return try {
        val skiaImage = Image.makeFromEncoded(this)
        skiaImage.toComposeImageBitmap()
    } catch (e: Exception) {
        null
    }
}