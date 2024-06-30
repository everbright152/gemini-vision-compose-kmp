package data.response
import  kotlinx.serialization.Serializable
import  kotlinx.serialization.SerialName

@Serializable
data class GeminiResponse(
    @SerialName("candidates")
    val cadidates : List<GeminiCadidate> = emptyList()
)

@Serializable
data class GeminiCadidate(
    @SerialName("content")
    val content : GeminiContent
)


@Serializable
data class GeminiContent(
    @SerialName("parts")
    val parts : List<GeminiPart> = emptyList()
)

@Serializable
data class GeminiPart(
    @SerialName("text")
    val text : String
)
