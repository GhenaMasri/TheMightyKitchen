package com.example.myapplication.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

suspend fun getOpenAIResponse(userInput: String, apiKey: String): String {
    val url = URL("http://192.168.10.31:5000/question")
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "POST"
    connection.setRequestProperty("Authorization", apiKey)
    connection.setRequestProperty("Content-Type", "application/json")

    val requestBody = """
        {
            "role":"user","content":"$userInput"
        }
    """.trimIndent()

    connection.doOutput = true
    connection.doInput = true

    val outputStream = OutputStreamWriter(connection.outputStream)
    outputStream.write(requestBody)
    outputStream.flush()

    val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
    val response = StringBuilder()
    var inputLine: String?
    while (inputStream.readLine().also { inputLine = it } != null) {
        response.append(inputLine)
    }
    inputStream.close()

    outputStream.close()
    connection.disconnect()

    return response.toString()
}
