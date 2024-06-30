package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.request.RequestBody
import di.network.GeminiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeminiViewModel(
    private val api: GeminiApi
) : ViewModel() {

    private val _promptResult = MutableStateFlow("")
    val promptResult = _promptResult.asStateFlow()

    fun getContent(prompt : String){
        viewModelScope.launch{
            val body = RequestBody.create(
                prompt = prompt
            )
            val result =  api.generateContent(body)
                .cadidates.firstOrNull()
                ?.content
                ?.parts?.firstOrNull()
                ?.text.orEmpty()
            withContext(Dispatchers.Main){
                _promptResult.value = result
            }
        }
    }

    fun getContentWithAttachment(prompt : String, image:Pair<String, String>){
        viewModelScope.launch{
            val body = RequestBody.createWithImage(
                prompt = prompt,
                image = image
            )

            val result =  api.generateContentWithImage(body)
                .cadidates.firstOrNull()
                ?.content
                ?.parts?.firstOrNull()
                ?.text.orEmpty()
            withContext(Dispatchers.Main){
                _promptResult.value = result
            }
        }
    }
}