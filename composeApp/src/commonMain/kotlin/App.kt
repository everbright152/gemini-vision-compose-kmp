import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import ui.GeminiApp

@Composable
@Preview
fun App() {
    MaterialTheme {
        GeminiApp()
    }

}