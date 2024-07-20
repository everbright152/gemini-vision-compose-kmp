package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.items.Item
import data.request.RequestBody
import di.network.GeminiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import state.LoadState

class GeminiViewModel(
    private val api: GeminiApi
) : ViewModel() {



    private var _isLoading =MutableStateFlow(false)
    var isLoading = _isLoading.asStateFlow()
    private var _listItem = arrayListOf<Item>()
    private val _promptResult = MutableStateFlow(emptyList<Item>())
    val promptResult = _promptResult.asStateFlow()



    fun onGreatingAI(){
        _listItem.add(Item(true, "Hi My Name Gemini,\nI'm Your Virtual Assistent May I Can Help You?"))
        addItem()
    }

    fun getContent(prompt : String){
       _isLoading.value=true
        _listItem.add(Item(false, prompt, isLoading = true))
        addItem()
        try {
            viewModelScope.launch{
                val body = RequestBody.create(
                    prompt = prompt
                )
                val result =  api.generateContent(body)
                    .cadidates.firstOrNull()
                    ?.content
                    ?.parts?.firstOrNull()
                    ?.text.orEmpty()
               withContext(Dispatchers.IO){
                   println("resulttt $result")
                   _listItem.add(Item(true, result))
                   addItem()
                   _isLoading.value=false
                }
            }
        }catch (e :Exception){
            _isLoading.value=false
            println("throwww ${e.cause?.message}")
        }

    }

    private fun addItem(){
        _promptResult.value = emptyList()
        _promptResult.value += _listItem
    }

    fun getContentWithAttachment(prompt : String, image:Pair<String, String>){
        _isLoading.value=true
        _listItem.add(Item(false, prompt, true, image.second))
        addItem()
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
                _listItem.add(Item(true, result))
                addItem()
                _isLoading.value=false
            }
        }
    }
}