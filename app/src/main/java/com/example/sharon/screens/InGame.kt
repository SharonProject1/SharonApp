package com.example.sharon.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharon.ui.theme.Green
import com.example.sharon.ui.theme.Yellow
import com.example.sharon.ui.theme.Red
import com.example.sharon.ui.theme.SharonTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// 완성
class InGame {
    companion object {
        @Composable
        fun InGameScreen(idInput: String, configuration: Configuration, nextScreen: () -> Unit) {
            val screenWidth: Int = configuration.screenWidthDp
            val screenHeight: Int = configuration.screenHeightDp


            val apiService = remember { createApiService() }
            var checkResponse by remember {
                mutableStateOf(Checkconnection(connect = "tru", needToUpdate = true, string = "서승준병신"))
            }
            var gameState by remember { mutableStateOf(GameState(data = listOf())) }
            var timeLeft by remember { mutableStateOf(1) }
            var numberOfAlivePlayers by remember { mutableStateOf(1) }
            var numberOfPlayers by remember { mutableStateOf(1) }
            LaunchedEffect(Unit) {
                while(true)
                {
                    try {
                        withContext(Dispatchers.IO)
                        {
                            var getstate = apiService.getGameState()
                            gameState = getstate
                        }
                        timeLeft = gameState.data[8].toInt()
                        numberOfAlivePlayers = gameState.data[6].toInt()
                        numberOfPlayers = gameState.data[5].toInt()
                        delay(100)
                    }
                    catch (e: Exception)
                    {
                        println("도비")
                    }

                }
            }

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if(timeLeft > 90) {
                                Text(
                                    text = "$timeLeft",
                                    fontSize = (screenWidth * 25/100).sp,
                                    color = Green
                                )
                            } else if(timeLeft in 31..90) {
                                Text(
                                    text = "$timeLeft",
                                    fontSize = (screenWidth * 25/100).sp,
                                    color = Yellow
                                )
                            } else {
                                Text(
                                    text = "$timeLeft",
                                    fontSize = (screenWidth * 25/100).sp,
                                    color = Red
                                )
                            }
                            Text(
                                text = "남은 시간(초)",
                                fontSize = (screenWidth * 7/100).sp
                            )
                        }

                        Spacer(modifier = Modifier.height((screenHeight * 10/100).dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if(numberOfAlivePlayers/numberOfPlayers.toFloat() > 2/3f) {
                                Text(
                                    text = "$numberOfAlivePlayers/$numberOfPlayers",
                                    fontSize = (screenWidth * 20/100).sp,
                                    color = Green
                                )
                            } else if(numberOfAlivePlayers/numberOfPlayers.toFloat() <= 2/3f && numberOfAlivePlayers/numberOfPlayers.toFloat() > 1/3f) {
                                Text(
                                    text = "$numberOfAlivePlayers/$numberOfPlayers",
                                    fontSize = (screenWidth * 20/100).sp,
                                    color = Yellow
                                )
                            } else {
                                Text(
                                    text = "$numberOfAlivePlayers/$numberOfPlayers",
                                    fontSize = (screenWidth * 20/100).sp,
                                    color = Red
                                )
                            }
                            Text(
                                text = "현재 생존자(명)",
                                fontSize = (screenWidth * 7/100).sp
                            )
                        }
                        Button(
                            onClick = {nextScreen()},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("다음 화면")
                        }
                    }
                }
            }
            var eliminated by remember { mutableStateOf(false) }
            var Success by remember { mutableStateOf(false) }

            var firstEliminated by remember { mutableStateOf(false) }
            var firstSuccess by remember { mutableStateOf(false) }
            LaunchedEffect(eliminated) {
                if (!firstEliminated)
                {
                    firstEliminated = true
                }
                else
                {
                    withContext(Dispatchers.IO)
                    {
                        apiService.sendFailed(idInput)
                        /* 팝업 띄우소*/
                    }
                }
            }
            LaunchedEffect(Success) {
                if (!firstSuccess)
                {
                    firstSuccess = true
                }
                else
                {
                    withContext(Dispatchers.IO)
                    {
                        apiService.sendSuccess(idInput)
                        /*팝업 띄우소*/
                    }
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun InGameScreenPreview() {
    SharonTheme {
        InGame.InGameScreen(idInput = "1",LocalConfiguration.current, nextScreen = {})
    }
}