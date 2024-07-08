package com.example.chatbotai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatbotai.core.DependencyInjection
import com.example.chatbotai.R
import com.example.chatbotai.application.chat.ChatState
import com.example.chatbotai.application.chat.ChatViewModel
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    chatViewModel: ChatViewModel = viewModel(),
) {
    val recordAudioController = DependencyInjection.getRecordAudioController()
    var prompt by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }
    val uiState by chatViewModel.chatState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    remember {
        scope.launch {
            chatViewModel.startChat("main", context)
            recordAudioController.init(context)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Title",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Button(
                onClick = {
                    recordAudioController.startListening { result ->
                        prompt = result
                        chatViewModel.sendMessage(prompt, context)
                    }
                },
                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "record")
            }

        }

        Text(text = prompt, modifier = Modifier.padding(16.dp))

        if (uiState is ChatState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var textColor = MaterialTheme.colorScheme.onSurface
            if (uiState is ChatState.Error) {
                textColor = MaterialTheme.colorScheme.error
                result = (uiState as ChatState.Error).errorMessage
            } else if (uiState is ChatState.Success) {
                textColor = MaterialTheme.colorScheme.onSurface
                result = (uiState as ChatState.Success).outputChat.joinToString("\n")
            }
            val scrollState = rememberScrollState()
            Text(
                text = result,
                textAlign = TextAlign.Start,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )
        }
    }
}