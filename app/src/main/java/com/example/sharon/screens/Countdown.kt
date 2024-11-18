package com.example.sharon.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.delay

class Countdown {
    companion object {
        @Composable
        fun CountdownScreen(nextScreen: () -> Unit) {
            var containerSize by remember { mutableStateOf(Size.Zero) }
            var count by remember { mutableStateOf(4) }

            LaunchedEffect(Unit) {
                while (count >= 0) {
                    if(count == 4) delay(3000)
                    else delay(1000)
                    count--
                }
                nextScreen()
            }

            val displayText = if(count in 1..3) "$count" else "시작"

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                            .onGloballyPositioned { coordinates ->
                                containerSize = coordinates.size.toSize()
                            }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            if(count == 4) {
                                Text(
                                    text = "이제 게임이 시작됩니다...",
                                    fontSize = (containerSize.width * 3 / 100).toInt().sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height((containerSize.height * 2/100).toInt().dp))
                                Text(
                                    text = "출발선에 서 주십시오.",
                                    fontSize = (containerSize.width * 3/100).toInt().sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            } else {
                                Text(
                                    text = displayText,
                                    fontSize = (containerSize.width * 3*(5 - count) / 100).toInt().sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}