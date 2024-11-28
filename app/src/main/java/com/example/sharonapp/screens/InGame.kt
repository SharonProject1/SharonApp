package com.example.sharonapp.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sharonapp.ui.theme.Green
import com.example.sharonapp.ui.theme.Yellow
import com.example.sharonapp.ui.theme.Red
import com.example.sharonapp.ui.theme.SharonAppTheme

// 완성
class InGame {
    companion object {
        @Composable
        fun InGameScreen(configuration: Configuration, gameState: List<Int> = listOf(180, 10, 10), navController: NavHostController) {
            val screenWidth: Int = configuration.screenWidthDp
            val screenHeight: Int = configuration.screenHeightDp

            val timeLeft: Int = gameState[0]
            val numberOfPlayers: Int = gameState[1]
            val numberOfAlivePlayers: Int = gameState[2]

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
                            onClick = { navController.navigate("termination") },
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
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun InGameScreenPreview() {
//    SharonAppTheme {
//        InGame.InGameScreen(LocalConfiguration.current, nextScreen = {})
//    }
//}