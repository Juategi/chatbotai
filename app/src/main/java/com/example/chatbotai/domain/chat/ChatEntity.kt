package com.example.chatbotai.domain.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey() val id: String,
    val chats: List<String>,
)
