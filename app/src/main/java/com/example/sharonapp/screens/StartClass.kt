package com.example.sharonapp.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.delay

class StartClass {
    companion object {
        @Composable
        fun StartScreen(
            onNavigateToHome: () -> Unit
        ) {
            BackHandler { }

            val screenWidth: Int = LocalConfiguration.current.screenWidthDp

            LaunchedEffect(Unit) {
                delay(2000)
                onNavigateToHome()
            }

            Scaffold(
                bottomBar = {
                    BottomAppBar (
                        containerColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "by team 4.5",
                            fontSize = (screenWidth * 5/100).sp,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            ) { innerPadding ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "무궁화 꽃이",
                        fontSize = (screenWidth * 15/100).sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "피었습니다!",
                        fontSize = (screenWidth * 15/100).sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.weight(0.7f))
                }
            }
        }
    }
}