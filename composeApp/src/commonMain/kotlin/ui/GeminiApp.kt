package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import di.Providers

@Composable
fun GeminiApp(
    viewModel: GeminiViewModel = Providers.viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val promptResult by viewModel.promptResult.collectAsState()

    LaunchedEffect(Unit){
        val prompt = "Apakah aplikasi BRImo itu milik BRI?"
       // val image Pair("image/webp", constant.attachment)
       // viewModel.   getContentWithAttachment(prompt, image)
         viewModel.getContent(prompt)
    }


    Text(promptResult)
}