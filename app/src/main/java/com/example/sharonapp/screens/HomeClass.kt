package com.example.sharonapp.screens

import android.content.res.Configuration
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        fun HomeScreen(configuration: Configuration, nextScreen: () -> Unit): String {
            val screenWidth = configuration.screenWidthDp
            val screenHeight = configuration.screenHeightDp
            var codeInput by remember { mutableStateOf("") }

            val coroutineScope = rememberCoroutineScope()
            val apiService2 = remember { SecondApiService() }
            var idInput by remember { mutableStateOf("") }


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
                    ) {
                        Text(
                            text = "무궁화 꽃이",
                            fontSize = (screenWidth * 10/100).sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "피었습니다!",
                            fontSize = (screenWidth * 10/100).sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 10/100).dp))
                        TextField(
                            value = codeInput,
                            onValueChange = { codeInput = it },
                            placeholder = { Text(text = "대기실 코드") },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        TextField(
                            value = idInput,
                            onValueChange = { idInput = it },
                            placeholder = { Text(text = "닉네임") },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    try{
                                        withContext(Dispatchers.IO) {
                                            apiService2.submitNickname(idInput)
                                        }
                                        nextScreen()
                                    } catch (e: Exception) {
                                        // 에러만 처리
                                        nextScreen()
                                    }
                                }
                                      },

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
            return idInput
        }
    }
}