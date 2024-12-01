package com.example.sharonapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharonapp.utility.SecondApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeClass {
    companion object {

        @Composable
        fun HomeScreen(
            onNavigateToWaitingRoom: (idInput: String) -> Unit
        ) {
            val screenWidth: Int = LocalConfiguration.current.screenWidthDp

            var codeInput by remember { mutableStateOf("") }
            var idInput by remember { mutableStateOf("") }

            val focusManager = LocalFocusManager.current
            val isButtonEnabled = codeInput.isNotBlank() && idInput.isNotBlank()

            val coroutineScope = rememberCoroutineScope()
            val apiService2 = remember { SecondApiService() }

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(innerPadding)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.weight(12f))
                        Text(
                            text = "무궁화 꽃이",
                            fontSize = (screenWidth * 10/100).sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "피었습니다!",
                            fontSize = (screenWidth * 10/100).sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.weight(6f))
                        TextField(
                            value = codeInput,
                            onValueChange = {
                                codeInput = it
                            },
                            placeholder = { Text(text = "대기실 코드") },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextField(
                            value = idInput,
                            onValueChange = {
                                idInput = it
                            },
                            placeholder = { Text(text = "닉네임") },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            enabled = isButtonEnabled,
                            onClick = {
                                coroutineScope.launch {
                                    try{
                                        withContext(Dispatchers.IO) {
                                            apiService2.submitNickname(idInput)
                                        }
                                        onNavigateToWaitingRoom(idInput)
                                    } catch (e: Exception) {
                                        onNavigateToWaitingRoom(idInput)
                                    }
                                }
                            },

                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "게임 시작")
                        }
                        Spacer(modifier = Modifier.weight(12f))
                    }
                }
            }
        }
    }
}