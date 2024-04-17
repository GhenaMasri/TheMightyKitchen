package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import getOpenAIResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Model class for chat message
data class ChatMessage(
    val text: String, val isUserMessage: Boolean
)
@Serializable
data class UserInput(val role: String, val content: String)

class ChatViewModel : ViewModel() {
    private val serverUrl = "http://51.12.247.61:443/question"
    private val _chatMessages = mutableStateOf<List<ChatMessage>>(emptyList())
    val chatMessages: MutableState<List<ChatMessage>> = _chatMessages

    @OptIn(InternalAPI::class)
    fun sendMessage(userInput: String) {
        viewModelScope.launch {
            try {
                // Add user message to chat messages
                val userMessage = ChatMessage(userInput, isUserMessage = true)
                _chatMessages.value = _chatMessages.value + userMessage

                // Make an HTTP GET request to google.com
                //val client = HttpClient(Android)
                val client = HttpClient()
                val userInput1 = UserInput("user", userInput)

                // Encode the UserInput object to JSON
                val jsonBody = Json.encodeToString(userInput1)
                println(jsonBody)
                // Make HTTP POST request with JSON data
                val response: HttpResponse = client.post("http://51.12.247.61:443/question") {
                    body = jsonBody
                }
                println("response: "+response.body())

                // Add bot response to chat messages
                val botMessage = ChatMessage(response.body(), isUserMessage = false)
                _chatMessages.value = _chatMessages.value + botMessage
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

}

@Composable
fun MessageItem(message: ChatMessage) {
    val backgroundColor =
        if (message.isUserMessage) MaterialTheme.colorScheme.primary else Color.White
    val alignment = if (message.isUserMessage) Alignment.TopEnd else Alignment.TopStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), contentAlignment = alignment
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ), modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = message.text, modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatPage(viewModel: ChatViewModel) {
    val chatMessages = viewModel.chatMessages.value
    var userInput by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn {
            items(chatMessages) { message ->
                MessageItem(message)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                placeholder = { Text("Type a message...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    viewModel.sendMessage(userInput)
                    userInput = ""
                    keyboardController?.hide()
                })
            )

            IconButton(
                onClick = {
                    viewModel.sendMessage(userInput)
                    userInput = ""
                }, modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessageItem() {
    MessageItem(ChatMessage("Hello", true))
}

@Preview
@Composable
fun PreviewChatPage() {
    val viewModel = ChatViewModel()
    ChatPage(viewModel)
}