package com.example.myapplication



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ChatPage(modifier: Modifier = Modifier)  {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat Page", fontSize = 22.sp)

        /*
            we need to set-up api to connect to the python server for the azure-OpenAi
            since it didn't worked with me to connect from here.
         */
        /*
//        i think i will need the following imports

//            import android.os.Bundle
//            import androidx.appcompat.app.AppCompatActivity
//            import kotlinx.coroutines.Dispatchers
//            import kotlinx.coroutines.GlobalScope
//            import kotlinx.coroutines.launch
//            import okhttp3.MediaType.Companion.toMediaType
//            import okhttp3.OkHttpClient
//            import okhttp3.Request
//            import okhttp3.RequestBody.Companion.toRequestBody
//            import org.json.JSONObject

        // Make HTTP request to fetch items
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val requestBody = jsonData.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url("http://server_ip:5000/submit_data") // our server IP and endpoint
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("Unexpected code $response")

                val responseData = response.body?.string()
                // Handle the response as needed
                println(responseData)
            }
        }
         */

    }
}

@Preview
@Composable
private fun OrderPagePreview(){
    MyApplicationTheme {
        ChatPage()
    }
}