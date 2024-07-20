import androidx.compose.ui.graphics.ImageBitmap

interface ImageBitmap {
    val imageBitmap: ImageBitmap
}

expect fun decodeBase64ToImageBitmap(base64: String): ImageBitmap?

class CommonHelper{

    companion object {
        fun  convertDecodeBase64ToImageBitmap(base64:String) :ImageBitmap? {
          return  decodeBase64ToImageBitmap(base64)
        }
    }

}