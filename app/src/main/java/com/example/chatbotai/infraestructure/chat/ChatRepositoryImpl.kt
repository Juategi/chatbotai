package com.example.chatbotai.infraestructure.chat

import android.content.Context
import com.example.chatbotai.domain.chat.ChatEntity
import com.example.chatbotai.domain.chat.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton

class ChatRepositoryImpl : ChatRepository {
    override suspend fun getChat(id: String, context: Context) = ChatDatabase.getDatabase(context).chatDao().getChat(id)

    override suspend fun insert(chat: ChatEntity, context: Context) = ChatDatabase.getDatabase(context).chatDao().insert(chat)
}