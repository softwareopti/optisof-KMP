package cl.optisoft.doctors

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory

actual fun ByteArray.toImageBitmap(): ImageBitmap? {
    return BitmapFactory.decodeByteArray(this, 0, size)?.asImageBitmap()
}
