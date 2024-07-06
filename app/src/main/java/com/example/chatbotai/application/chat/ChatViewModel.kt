package com.example.chatbotai.application.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbotai.BuildConfig
import com.example.chatbotai.domain.chat.ChatEntity
import com.example.chatbotai.domain.chat.ChatRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel : ViewModel()  {

    @Inject
    lateinit var chatRepository: ChatRepository
    private lateinit var chatAI: Chat

    private val _chatState: MutableStateFlow<ChatState> =
        MutableStateFlow(ChatState.Initial)

    val chatState: StateFlow<ChatState> =
        _chatState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = BuildConfig.apiKey
    )

    fun startChat(chat: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _chatState.value = ChatState.Loading
            val chatEntity = chatRepository.getChat(chat, context) ?: ChatEntity(chat, emptyList())
            val messages: List<Content> =
                mutableListOf(Content(parts = chatEntity.chats.map { TextPart(text = it) }))
            chatAI = generativeModel.startChat(messages)
            _chatState.value = ChatState.Success(chatEntity.chats)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _chatState.value = ChatState.Loading
            try {
                val chatRes = chatAI.sendMessage(message)
                val messages = getChatMessages()
                _chatState.value = ChatState.Success(messages.plus(chatRes.text!!))
            } catch (e: Exception) {
                _chatState.value = ChatState.Error(e.localizedMessage ?: "")
            }
        }
    }

    fun saveChat(chat: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val messages = getChatMessages()
            chatRepository.insert(ChatEntity(chat, messages), context)
        }
    }

    private fun getChatMessages(): List<String> {
        val messages: List<String> = emptyList()
        chatAI.history.forEach { content ->
            content.parts.forEach {
                val part: TextPart = it as TextPart
                messages.plus(part.text)
            }
        }
        return messages
    }
}