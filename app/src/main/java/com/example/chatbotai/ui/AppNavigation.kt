package com.example.chatbotai.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { ChatSelectorScreen(navController) }
        composable("chat/{name}") { backStackEntry -> ChatScreen(navController, backStackEntry.arguments?.getString("name")) }
    }
}
