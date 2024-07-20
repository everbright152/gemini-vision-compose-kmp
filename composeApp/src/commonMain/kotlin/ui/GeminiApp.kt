package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import book_gemini.composeapp.generated.resources.Res
import book_gemini.composeapp.generated.resources.baseline_photo_camera_24
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import di.Providers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.component.AIItem
import ui.component.ImageDialog
import ui.component.LoadingItem
import ui.component.MyItem
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
fun GeminiApp(
    viewModel: GeminiViewModel = Providers.viewModel(),
    navController: NavHostController = rememberNavController(),
    onErroMessage : (String) -> Unit = {}
) {

    var isShowDialogImage by remember{ mutableStateOf(false) }
    var imageBase64 by remember { mutableStateOf("") }
    val promptResult by viewModel.promptResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit){
        viewModel.onGreatingAI()
    }

    LaunchedEffect(isLoading){
        coroutineScope.launch {
            val maxIndex = promptResult.size
            listState.animateScrollToItem(maxIndex)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 10.dp)
    ) {

        LazyColumn(modifier = Modifier.weight(1f),
            state = listState) {
            itemsIndexed(promptResult){index, item ->
                if (item.isAI)
                    AIItem(item)
                else MyItem(Modifier, item)
            }
            item {
                if (isLoading){
                    LoadingItem()
                }
            }

        }

        AnimatedVisibility(visible = !isLoading  ){
            TypeArea(viewModel,  {value->
                isShowDialogImage = value.first
                imageBase64 = value.second
            },onErroMessage)
        }

    }


    ImageDialog(isShowDialogImage, imageBase64){ result->
        isShowDialogImage=false
        if (result.isNotEmpty()) {
            //first for type image
            //second for base64
            val post = Pair("image/png", imageBase64)
            viewModel.getContentWithAttachment(result, post)
        }
        imageBase64 = ""
    }

}

@OptIn(ExperimentalResourceApi::class, ExperimentalEncodingApi::class)
@Composable
fun TypeArea(viewModel: GeminiViewModel, showDialogImage : (Pair<Boolean, String>)->Unit, onErroMessage: (String) -> Unit) {
    val scope = rememberCoroutineScope()

    var prompt by remember { mutableStateOf(TextFieldValue("")) }

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                showDialogImage( Pair(true, Base64.encode(it)))
            }
        }
    )

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
                singleImagePicker.launch()
            }
        ) {
            Icon(painter = painterResource(Res.drawable.baseline_photo_camera_24), contentDescription = null)
        }


        Spacer(Modifier.width(5.dp))
        IconButton(modifier = Modifier.background(
            color = Color(primary),
            shape = RoundedCornerShape(50)
        ),
            onClick = {
                if (prompt.text.isEmpty())
                    onErroMessage("Empty command not allow, Type Something Please...")
                else {
                    viewModel.getContent(prompt.text)
                    prompt = TextFieldValue("")
                }


            }
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = null)
        }

    }
}