package com.example.sharon.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

class Home {
    companion object {
        @Composable
        fun HomeScreen(nextScreen: () -> Unit) {
            var containerSize by remember { mutableStateOf(Size.Zero) }
            var codeInput by remember { mutableStateOf(TextFieldValue("")) }

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .onGloballyPositioned { coordinates ->
                                containerSize = coordinates.size.toSize()
                            }
                    ) {
                        Text(
                            text = "무궁화 꽃이",
                            fontSize = (containerSize.width * 5/100).toInt().sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "피었습니다!",
                            fontSize = (containerSize.width * 5/100).toInt().sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height((containerSize.height * 5/100).toInt().dp))
                        TextField(
                            value = codeInput,
                            onValueChange = { codeInput = it },
                            placeholder = { Text(text = "코드 입력") },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.height((containerSize.height * 1/100).toInt().dp))
                        Button(
                            onClick = { nextScreen() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "게임 시작")
                        }
                    }
                }
            }
        }
    }
}