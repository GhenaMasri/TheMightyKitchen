package com.example.myapplication.navigation

import com.example.myapplication.ChatPage
import com.example.myapplication.ChatViewModel
import com.example.myapplication.OrderPage
import com.example.myapplication.ui.theme.MyApplicationTheme



import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val linear = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.primaryContainer,
        )
    )

    Box(
        modifier = modifier
            .background(linear)
    ) {
        Scaffold(
            modifier = Modifier,
            containerColor = Color.Transparent,
            bottomBar = {
                MyBottomNavBar(navController)
            },
            topBar = { TopBar(navController) },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screens.Orders.name,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route = Screens.Orders.name) {
                    OrderPage()
                }
                composable(route = Screens.Chat.name) {
                    ChatPage(ChatViewModel())
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MyAppPreview() {
    MyApplicationTheme {
        MyApp(modifier = Modifier)
    }
}