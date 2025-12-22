package cl.optisoft.doctors.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cl.optisoft.doctors.toImageBitmap
import coil3.compose.rememberAsyncImagePainter
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DoctorPhotoSection(
    photoBytes: ByteArray?,
    photoUrl: String?,
    isEditMode: Boolean,
    onPickImage: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            when {
                // 🔥 Si hay bytes, usar ImageBitmap (funciona en Android/iOS)
                photoBytes != null -> {
                    val bitmap = remember(photoBytes) { photoBytes.toImageBitmap() }

                    if (bitmap != null) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                // 🔥 Si viene de backend (URL)
                photoUrl != null -> {
                    KamelImage(
                        resource = asyncPainterResource(photoUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                // 🔥 Si no hay foto
                else -> {
                    Box(contentAlignment = Alignment.BottomEnd) {

                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF1F3F4)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier
                                .offset(x = 6.dp, y = 6.dp)
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { onPickImage() }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(6.dp))

        if (!isEditMode && photoBytes == null && photoUrl == null) {
            Text(
                "Upload Image",
                color = Color(0xFF00A9A5),
                modifier = Modifier.clickable { onPickImage() }
            )
        }

        Spacer(Modifier.height(6.dp))
    }
}

@Preview
@Composable
fun preview(){
    Column(modifier = Modifier.background(Color.White)) {
//        DoctorPhotoSection(null, false, onPickImage = {})
    }
}
