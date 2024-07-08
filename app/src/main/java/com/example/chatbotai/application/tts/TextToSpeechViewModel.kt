package com.example.chatbotai.application.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.Locale

class TextToSpeechViewModel : TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech

    fun init(context: Context) {
        tts = TextToSpeech(context, this)
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
            } else {
                // Initialization success.
                //logAvailableVoices()
                //changeVoice("es-es-x-eef-local")
            }
        } else {
            // Initialization failed.
        }
    }


}
