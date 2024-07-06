package com.example.chatbotai.application.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.Locale

abstract class TextToSpeechViewModel : ViewModel() , TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech

    fun init(context: Context) {
        tts = TextToSpeech(context, this)
        tts.setLanguage(Locale.ENGLISH)
        changeVoice("es-es-x-eef-local")
    }

    private fun changeVoice(voiceName: String) {
        val voices = tts.voices
        for (voice in voices) {
            if (voice.name == voiceName) {
                tts.voice = voice
                break
            }
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun logAvailableVoices() {
        val voices = tts.voices
        for (voice in voices) {
            Log.d("Voice", "Name: ${voice.name}, Locale: ${voice.locale}")
        }
    }

}
