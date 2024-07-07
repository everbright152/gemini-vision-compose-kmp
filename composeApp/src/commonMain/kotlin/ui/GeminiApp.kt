package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import di.Providers
import kotlinx.coroutines.launch
import state.LoadState
import ui.component.AIItem
import ui.component.MyItem

@Composable
fun GeminiApp(
    viewModel: GeminiViewModel = Providers.viewModel(),
    navController: NavHostController = rememberNavController(),
    onErroMessage : (String) -> Unit = {}
) {
    var prompt by remember { mutableStateOf(TextFieldValue("")) }


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
            .padding(horizontal = 10.dp)
    ) {

        LazyColumn(modifier = Modifier.weight(1f),
            state = listState) {
            itemsIndexed(promptResult){index, item ->
                if (item.isAI)
                    AIItem(item)
                else MyItem(Modifier, item)
            }


        }

        AnimatedVisibility(visible = !isLoading  ){
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

    }



}