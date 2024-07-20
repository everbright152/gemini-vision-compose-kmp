package di.network

import data.response.GeminiResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface GeminiApi {

    @POST("v1beta/models/${GEMINI_PRO}:generateContent?key=${KEY}")
    suspend fun generateContent(@Body requst :String): GeminiResponse


    @POST("v1beta/models/${GEMINI_PRO}:generateContent?key=${KEY}")
    suspend fun generateContentWithImage(@Body requst :String):GeminiResponse

    companion object{
        const val BASE_URL = "https://generativelanguage.googleapis.com/"
        private const val GEMINI_PRO= "gemini-1.5-flash" //"gemini-pro"

        private const val KEY ="YOUR API KEY HERE"
    }

}