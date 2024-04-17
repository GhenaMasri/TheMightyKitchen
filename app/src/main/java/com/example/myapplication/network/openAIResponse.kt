import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException

suspend fun getOpenAIResponse(userInput: String, serverUrl: String): String {
    try {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("role", "user")
            .add("content", userInput)
            .build()

        val request = Request.Builder()
            .url(serverUrl)
            .post(requestBody)
            .build()

        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                response.body()?.string() ?: throw IOException("Response body is null")
            }
        }
        println("response:"+response)
        return response
    } catch (e: Exception) {
        e.printStackTrace()
        throw e // re-throw the exception to propagate it
    }
}