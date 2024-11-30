package com.example.sharonapp.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharonapp.WaitingRoom
import com.example.sharonapp.ui.theme.Green
import com.example.sharonapp.ui.theme.Red
import com.example.sharonapp.ui.theme.White
import com.example.sharonapp.utility.Checkconnection
import com.example.sharonapp.utility.SecondApiService
import com.example.sharonapp.utility.ServerResponse
import com.example.sharonapp.utility.createApiService
import com.example.sharonapp.utility.isRunningResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class WaitingRoomClass {
    companion object {
        @Composable
        fun WaitingRoomScreen(
            waitingRoom: WaitingRoom,
            onNavigateToCountdown: (userId: String) -> Unit
        ) {
            val screenWidth: Int = LocalConfiguration.current.screenWidthDp
            val screenHeight: Int = LocalConfiguration.current.screenHeightDp
          
            val userId = waitingRoom.userId
          
            val focusManager = LocalFocusManager.current
            val isButtonEnabled = remember { mutableStateOf(false) }
            val isButtonOn = remember { mutableStateOf(false) }

            var startSignal by remember { mutableStateOf(false) }
            var connection by rememberSaveable { mutableStateOf(true) }
            val apiService = remember { createApiService() }
            var checkResponse by remember {
                mutableStateOf(
                    Checkconnection(
                        connect = "tru",
                        needToUpdate = true,
                        string = "서승준병신"
                    )
                )
            }
            
            var tempsignal by remember { mutableStateOf(isRunningResponse(data = false)) }
            var playerDataResponsed by remember { mutableStateOf(ServerResponse(data = listOf())) }
            var numberOfPlayers by remember { mutableIntStateOf(7) }
            var playerData by remember { mutableStateOf(listOf<List<String>>()) }
            
            LaunchedEffect(connection) {
                if (connection) {
                    while (connection) {
                        try {
                            withContext(Dispatchers.IO) {
                                val response = apiService.connectionCheck(userId)
                                val signal = apiService.isRunning(userId)
                                if (response.isSuccessful) {
                                // response.body()를 통해 Checkconnection 데이터 추출
                                checkResponse = response.body() ?: Checkconnection("false", false, "No Data")

                                tempsignal = signal.body() ?: isRunningResponse(false)
                                /*
                                startSignal = tempsignal.runningResponse
                                println("$startSignal 야동")
                                */
                            } else {
                                // 에러 처리
                                println("Error Response: ${response.errorBody()?.string()}")
                            }
                            }
                        } catch (e: Exception) {
                            println("$e 냠냠")
                        }

                        delay(500)
                    }
                }
            }

            LaunchedEffect(checkResponse.string) {
                if(checkResponse.needToUpdate) {
                    try {
                        playerDataResponsed = withContext(Dispatchers.IO) {
                            apiService.getPlayerData(userId)
                        }
                        playerData = playerDataResponsed.data
                        numberOfPlayers = playerDataResponsed.pCount
                    } catch (e: Exception) {
                        val templayerDataResponsedata = listOf(
                                  listOf("나 자신", "NaN", "false", "true", "false"),
                                  listOf("테스트용1", "1", "false", "true", "false"),
                                  listOf("테스트용2", "2", "false", "true", "false"),
                                  listOf("테스트용3", "3", "true", "true", "false"),
                                  listOf("테스트용4", "4", "false", "true", "false"),
                                  listOf("테스트용5", "5", "true", "true", "false"),
                                  listOf("테스트용6", "6", "false", "true", "false"),
                                  listOf("테스트용7", "7", "true", "true", "false"),
                                  listOf("테스트용8", "8", "false", "true", "false"),
                                  listOf("테스트용9", "9", "true", "true", "false")
                              )
                        playerData = templayerDataResponsedata
                        numberOfPlayers = templayerDataResponsedata.size
                    }
                }
            }

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        Text(
                            text = "Test Room",
                            fontSize = (screenWidth * 10/100).sp
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))

                        val cellSize = screenWidth * 30/100
                        LazyVerticalGrid(
                            horizontalArrangement = Arrangement.spacedBy((screenWidth * 2/100).dp, Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy((screenWidth * 2/100).dp, Alignment.CenterVertically),
                            columns = GridCells.FixedSize(cellSize.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(numberOfPlayers-1) { index ->
                                PlayerBox(
                                    size = cellSize,
                                    index = index+1,
                                    screenWidth = screenWidth,
                                    playerData = playerData
                                )

                          }
                        }
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        PlayerBox(
                            size = (screenHeight * 30 / 100),
                            index = 0,
                            doesTextFieldExists = true,
                            isButtonEnabled = isButtonEnabled,
                            isButtonOn = isButtonOn.value,
                            screenWidth = screenWidth,
                            playerData = playerData
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        Button(
                            onClick = {
                                isButtonOn.value = !isButtonOn.value
                            },
                            enabled = isButtonEnabled.value,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if(isButtonOn.value) Color(0x5000FF00) else Color(0xA000FF00)
                            )
                        ) {
                            Text("준비 완료")
                        }
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                    }
                }
            }

            val isFirstLaunch = remember { mutableStateOf(true) }
            LaunchedEffect(tempsignal.data) {
                startSignal = tempsignal.data
                println("$startSignal 야동")
                if(isFirstLaunch.value) {
                    isFirstLaunch.value = false
                }
                else if(startSignal) {
                     onNavigateToCountdown(userId)
                }
            }
        }
    }
}

@Composable
fun PlayerBox(
    size: Int,
    index: Int,
    doesTextFieldExists: Boolean = false,
    isButtonEnabled: MutableState<Boolean> = mutableStateOf(false),
    isButtonOn: Boolean = true,
    screenWidth: Int, playerData: List<List<String>>
) {
    if (playerData.isEmpty() || index >= playerData.size) {
        Text(text = "Loading...", fontSize = 16.sp) // 안전한 기본 UI
        return
    }
    var textState by remember { mutableStateOf("") }
    var figureColor = Red
    val isReady = playerData[index][2]
    val apiService = remember { createApiService() }
    var isInitialized by remember { mutableStateOf(false) }

    if (isReady == "true")
        figureColor = Green

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape((screenWidth * 5 / 100).dp))
            .background(color = Color.DarkGray)
            .size(size.dp)
            .aspectRatio(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = (screenWidth * 2 / 100).dp, bottom = (screenWidth * 2 / 100).dp)
                .fillMaxSize()
        ) {
            Text(
                text = playerData[index][0],
                fontSize = (size / 6).sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(figureColor)
                            .weight(1f)
                            .aspectRatio(1f)
                            .fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape((size * 64 / 30).dp, (size * 64 / 30).dp))
                            .background(figureColor)
                            .weight(2f)
                            .aspectRatio(1f)
                            .fillMaxSize()
                    ) {
                        if (doesTextFieldExists) {
                            TextField(
                                value = textState,
                                textStyle = TextStyle(
                                    fontSize = (size / 6).sp,
                                    lineHeight = (size / 4).sp,
                                    color = White,
                                    textAlign = TextAlign.Center
                                ),
                                onValueChange = {
                                    val isDuplicated = playerData.drop(1).any { player -> player[1] == it }

                                    if (it.all { it.isDigit() } && it.length <= 3) {
                                        textState = it
                                        isButtonEnabled.value = it.isNotEmpty() && !isDuplicated && (it.toInt() != 0)
                                    }
                                },
                                placeholder = {
                                    Text(
                                        text = "번호 입력",
                                        fontSize = (size * 8 / 100).sp,
                                        lineHeight = (size / 4).sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .fillMaxWidth()
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = White,
                                    unfocusedTextColor = Color.LightGray,
                                    disabledTextColor = Color.Gray,
                                    focusedContainerColor = Color(0x00000000),
                                    unfocusedContainerColor = Color(0x00000000),
                                    disabledContainerColor = Color(0x00000000),
                                    focusedIndicatorColor = Color(0x00000000),
                                    unfocusedIndicatorColor = Color(0x00000000),
                                    disabledIndicatorColor = Color(0x00000000),
                                    cursorColor = White
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                enabled = !isButtonOn,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
                            )
                        } else {
                            Text(
                                text = if(playerData[index][1] == "NaN") "-" else playerData[index][1],
                                fontSize = (size / 6).sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(isButtonOn) {
            if (isInitialized)
            {
            if (isButtonOn) {
                withContext(Dispatchers.IO) {
                    try {

                        val response = apiService.getInputNumber(nickname = playerData[0][0], number = textState.toInt() )
                        val response2 = apiService.sendReady(playerData[0][0]) // 준비 완료 전송

                        if (response.isSuccessful) {
                            Log.d("Server Response", response.body()?.string ?: "Empty Response")
                        } else {
                            Log.e("Error Response", response.errorBody()?.string() ?: "No Error Body")
                        }
                    } catch (e: Exception) {
                        Log.e("Network Error", e.message ?: "Unknown Error")
                    }

                }
            }
            else {
                withContext(Dispatchers.IO) {
                    try {
                        apiService.sendNotReady(playerData[0][0]) // 준비 취소 전송
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }
            }
            }
            else
            {
                isInitialized = true
            }
        }

    }
}