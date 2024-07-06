package com.example.chatbotai.infraestructure.chat

import com.example.chatbotai.domain.chat.ChatEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface ChatDao {
    @Insert
    suspend fun insert(chat: ChatEntity)

    @Query("SELECT * FROM chats WHERE id = :id")
    suspend fun getChat(id: String): ChatEntity?
}