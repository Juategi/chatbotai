package com.example.chatbotai.application.record

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class RecordAudioViewModel : ViewModel() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechRecognizerIntent: Intent

    fun init(context: Context) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }

    fun startListening(onResult: (String) -> Unit) {
        val recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                // Do nothing
            }

            override fun onBeginningOfSpeech() {
                // Do nothing
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Do nothing
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Do nothing
            }

            override fun onEndOfSpeech() {
                // Do nothing
            }

            override fun onError(error: Int) {
                //speechRecognizer.startListening(intent)
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.let {
                    onResult(it[0])
                }
                //speechRecognizer.startListening(intent)
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.let {
                    onResult(it[0])
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Do nothing
            }
        }
        speechRecognizer.setRecognitionListener(recognitionListener)
        speechRecognizer.startListening(speechRecognizerIntent)
    }


}