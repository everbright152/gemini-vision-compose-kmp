package di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import di.network.GeminiApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.GeminiViewModel

object Providers : KoinComponent {

    private val api : GeminiApi by inject()
    @Composable
    fun viewModel() = viewModel { GeminiViewModel(api) }
}