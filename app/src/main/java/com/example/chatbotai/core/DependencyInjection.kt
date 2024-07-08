package com.example.chatbotai.core

import com.example.chatbotai.application.record.RecordAudioController
import com.example.chatbotai.application.tts.TextToSpeechController
import com.example.chatbotai.domain.chat.ChatRepository
import com.example.chatbotai.domain.person.PersonRepository
import com.example.chatbotai.infraestructure.chat.ChatRepositoryImpl
import com.example.chatbotai.infraestructure.person.PersonRepositoryImpl

class DependencyInjection {

    object SingletonHolder {
        val recordAudioController: RecordAudioController = RecordAudioController()
        val textToSpeechController: TextToSpeechController = TextToSpeechController()
        val chatRepository: ChatRepository = ChatRepositoryImpl()
        val personRepository: PersonRepository = PersonRepositoryImpl()
    }

    companion object {
        fun getChatRepository(): ChatRepository {
            return SingletonHolder.chatRepository
        }
        fun getRecordAudioController(): RecordAudioController {
            return SingletonHolder.recordAudioController
        }
        fun getTextToSpeechController(): TextToSpeechController {
            return SingletonHolder.textToSpeechController
        }
        fun getPersonRepository(): PersonRepository {
            return SingletonHolder.personRepository
        }
    }

}