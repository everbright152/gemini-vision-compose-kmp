package ui.component

import CommonHelper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import book_gemini.composeapp.generated.resources.Res
import book_gemini.composeapp.generated.resources.baseline_photo_camera_24
import com.seiko.imageloader.rememberAsyncImagePainter
import okio.ByteString.Companion.decodeBase64
import org.jetbrains.compose.resources.painterResource
import ui.gray
import ui.primary
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
@Composable
fun ImageDialog(showDialog:Boolean, imageBase64:String , onClose:(String)->Unit) {
    val image = CommonHelper.convertDecodeBase64ToImageBitmap(imageBase64)
    var prompt by remember { mutableStateOf(TextFieldValue("")) }

    if (showDialog) {
        Dialog(onDismissRequest = {
            prompt = TextFieldValue("")
            onClose("") }) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(16.dp),
                color = Color.LightGray
            ) {
                Column  (
                     modifier = Modifier.wrapContentSize()
                ) {
                    image?.apply {
                        Image(
                            bitmap =  image  ,
                            contentDescription = null,
                            modifier = Modifier.size(500.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .background(color = Color(gray), shape = RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f),
                            value = prompt,
                            onValueChange = { newText ->
                                prompt = newText
                            },
                            placeholder = {
                                Text(text = "Type something")
                            }
                        )
                        Spacer(Modifier.width(5.dp))
                        IconButton(modifier = Modifier.background(
                            color = Color(primary),
                            shape = RoundedCornerShape(50)
                        ),
                            onClick = {
                                onClose(prompt.text)
                                prompt = TextFieldValue("")
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
                        }

                    }

                }
            }
        }
    }
}

