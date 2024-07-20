package data.items

data class Item(
    val isAI :Boolean,
    val value :String,
    val isImage :Boolean?=false,
    val baseImage64 :String?=null,
    val isLoading : Boolean?=false
)
