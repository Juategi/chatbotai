package com.example.chatbotai.domain.chat

import android.content.Context

interface ChatRepository {
    suspend fun getChat(id: String, context: Context): ChatEntity?
    suspend fun insert(chat: ChatEntity, context: Context)
}