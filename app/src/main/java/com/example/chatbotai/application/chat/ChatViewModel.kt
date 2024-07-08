package com.example.chatbotai.application.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbotai.BuildConfig
import com.example.chatbotai.core.DependencyInjection
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

class ChatViewModel : ViewModel()  {

    private var chatRepository: ChatRepository = DependencyInjection.getChatRepository()
    private var textToSpeechController = DependencyInjection.getTextToSpeechController()
    private lateinit var chatAI: Chat
    private lateinit var chatSession: String

    private val _chatState: MutableStateFlow<ChatState> =
        MutableStateFlow(ChatState.Initial)

    val chatState: StateFlow<ChatState> =
        _chatState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )

    fun startChat(chat: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _chatState.value = ChatState.Loading
            chatSession = chat
            textToSpeechController.init(context)
            val chatEntity = chatRepository.getChat(chat, context) ?: ChatEntity(chat, emptyList())
            if(chatEntity.chats.isEmpty()) {
                chatAI = generativeModel.startChat()
                _chatState.value = ChatState.Success(emptyList())
                return@launch
            }
            val messages: List<Content> = chatEntity.chats.map {Content(parts = listOf(TextPart(text = it)))}
            chatAI = generativeModel.startChat(messages)
            _chatState.value = ChatState.Success(chatEntity.chats)
        }
    }

    fun sendMessage(message: String, context: Context)  {
        viewModelScope.launch(Dispatchers.IO) {
            _chatState.value = ChatState.Loading
            try {
                val chatRes = chatAI.sendMessage(message)
                val messages = getChatMessages()
                _chatState.value = ChatState.Success(messages.plus(chatRes.text!!))
                //Speak chat
                textToSpeechController.speak(chatRes.text!!)
                //Save chat
                chatRepository.insert(ChatEntity(chatSession, messages), context)
            } catch (e: Exception) {
                _chatState.value = ChatState.Error(e.message ?: "")
            }
        }
    }


    private fun getChatMessages(): List<String> {
        var messages: List<String> = emptyList()
        chatAI.history.forEach { content ->
            content.parts.forEach {
                val part: TextPart = it as TextPart
                messages = messages.plus(part.text)
            }
        }
        return messages
    }
}