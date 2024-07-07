import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import di.appModule
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import ui.GeminiApp

@Composable
@Preview
fun App() {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            content = {
                                Text(
                                    text = data.message,
                                    style = MaterialTheme.typography.body2
                                )
                            },
                            backgroundColor = Color.Red,
                            action = {
                                data.actionLabel?.let { actionLabel ->
                                    TextButton(onClick = {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                    }) {
                                        Text(
                                            text = actionLabel,
                                        )
                                    }
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.Top)
                )
            }

        ) {
            GeminiApp() { message ->
                scope.launch {
                    snackbarHostState.showSnackbar(message/*, actionLabel = "OK"*/)
                }
            }
        }

    }

}