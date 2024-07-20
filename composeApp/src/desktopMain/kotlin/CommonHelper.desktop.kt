import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.util.decodeBase64Bytes
import org.jetbrains.skia.Image

actual fun decodeBase64ToImageBitmap(base64: String): ImageBitmap? {
    return try {
        Image.makeFromEncoded(base64.decodeBase64Bytes()).toComposeImageBitmap()
    } catch (e: Exception) {
        null
    }
}