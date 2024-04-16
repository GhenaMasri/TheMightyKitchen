package com.example.myapplication

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.ui.tooling.preview.Preview

// Model class for chat message
data class ChatMessage(
    val text: String, val isUserMessage: Boolean
)

class ChatViewModel : ViewModel() {
    private val _chatMessages = mutableStateOf<List<ChatMessage>>(emptyList())
    val chatMessages: MutableState<List<ChatMessage>> = _chatMessages

    fun sendMessage(userInput: String) {
        val userMessage = ChatMessage(userInput, isUserMessage = true)
        val botResponse = ChatMessage("Bot response for: $userInput", isUserMessage = false)
        val updatedMessages = (_chatMessages.value + userMessage + botResponse)
        _chatMessages.value = updatedMessages
    }/* viewModelScope.launch(Dispatchers.IO) {
            val botResponse = getOpenAIResponse(userInput, "YOUR_OPENAI_SERVER_URL_HERE", "YOUR_API_KEY_HERE")
            val userMessage = ChatMessage(userInput, isUserMessage = true)
            val botMessage = ChatMessage(botResponse, isUserMessage = false)
            _chatMessages.value = _chatMessages.value + userMessage + botMessage
        }*/
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

