package com.example.chatbotai.application.chat

/**
 * A sealed hierarchy describing the state of the text generation.
 */
sealed interface ChatState {

    /**
     * Empty state when the screen is first shown
     */
    object Initial : ChatState

    /**
     * Still loading
     */
    object Loading : ChatState

    /**
     * Text has been generated
     */
    data class Success(val outputChat: List<String>) : ChatState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String) : ChatState
}