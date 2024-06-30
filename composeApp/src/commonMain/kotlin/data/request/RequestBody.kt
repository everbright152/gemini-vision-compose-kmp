package data.request

import  kotlinx.serialization.Serializable
import  kotlinx.serialization.SerialName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class RequestBody(
    @SerialName("contents")
    val content: List<RequestContent>
) {
    companion object {
        fun createWithImage(prompt: String, image: Pair<String, String>): String {
            val body = RequestBody(
                content = listOf<RequestContent>(
                    RequestContent(
                        parts = listOf(
                            RequestPart(
                                text = prompt,
                                data = RequestPartData(
                                    mimeType = image.first,
                                    data = image.second
                                )
                            )
                        )
                    )
                ),

                )
            return Json.encodeToString(body)
        }

        fun create(prompt: String): String {
            val body = RequestBody(
                content = listOf(
                    RequestContent(
                        parts = listOf(
                            RequestPart(
                                text = prompt
                            )
                        )
                    )
                )
            )
            return Json.encodeToString(body)
        }
    }
}

@Serializable
data class RequestContent(
    @SerialName("parts")
    val parts: List<RequestPart>
)

@Serializable
data class RequestPart(
    @SerialName("text")
    val text: String ,

    @SerialName("inlindeData")
    val data: RequestPartData? = null
)

@Serializable
data class RequestPartData(

    @SerialName("mimeType")
    val mimeType: String,
    @SerialName("data")
    val data: String,
)

