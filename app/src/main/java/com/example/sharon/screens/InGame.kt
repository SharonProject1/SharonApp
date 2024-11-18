package com.example.sharon.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

class InGame {
    companion object {
        @Composable
        fun InGameScreen(nextScreen: () -> Unit) {
            var containerSize by remember { mutableStateOf(Size.Zero) }

            val configuration = LocalConfiguration.current
            val orientation = configuration.orientation

            val timeLeft by remember { mutableStateOf(180) }
            val numberOfPlayers by remember { mutableStateOf(10) }
            val numberOfCurrentSurvivors by remember { mutableStateOf(numberOfPlayers) }

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(innerPadding)
                            .onGloballyPositioned { coordinates ->
                                containerSize = coordinates.size.toSize()
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                if(timeLeft > 90) {
                                    Text(
                                        text = "$timeLeft",
                                        fontSize = (containerSize.height * 15/100).toInt().sp,
                                        lineHeight = (containerSize.height * 20/100).toInt().sp,
                                        color = Color(0xff00dd00)
                                    )
                                } else if(timeLeft in 31..90) {
                                    Text(
                                        text = "$timeLeft",
                                        fontSize = (containerSize.height * 15/100).toInt().sp,
                                        lineHeight = (containerSize.height * 20/100).toInt().sp,
                                        color = Color(0xfff8d000)
                                    )
                                } else {
                                    Text(
                                        text = "$timeLeft",
                                        fontSize = (containerSize.height * 15/100).toInt().sp,
                                        lineHeight = (containerSize.height * 20/100).toInt().sp,
                                        color = Color(0xfff00000)
                                    )
                                }
                                Text(
                                    text = "남은 시간(초)",
                                    fontSize = (containerSize.height * 2/100).toInt().sp
                                )
                            }
                            Spacer(modifier = Modifier.width((containerSize.height * 5/100).toInt().dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                if(numberOfCurrentSurvivors/numberOfPlayers.toFloat() > 2/3f) {
                                    Text(
                                        text = "$numberOfCurrentSurvivors/$numberOfPlayers",
                                        fontSize = (containerSize.height * 8/100).toInt().sp,
                                        lineHeight = (containerSize.height * 20/100).toInt().sp,
                                        color = Color(0xff00dd00)
                                    )
                                } else if(numberOfCurrentSurvivors/numberOfPlayers.toFloat() <= 2/3f && numberOfCurrentSurvivors/numberOfPlayers.toFloat() > 1/3f) {
                                    Text(
                                        text = "$numberOfCurrentSurvivors/$numberOfPlayers",
                                        fontSize = (containerSize.height * 8/100).toInt().sp,
                                        lineHeight = (containerSize.height * 20/100).toInt().sp,
                                        color = Color(0xfff8d000)
                                    )
                                } else {
                                    Text(
                                        text = "$numberOfCurrentSurvivors/$numberOfPlayers",
                                        fontSize = (containerSize.height * 8/100).toInt().sp,
                                        lineHeight = (containerSize.height * 20/100).toInt().sp,
                                        color = Color(0xfff00000)
                                    )
                                }
                                Text(
                                    text = "현재 생존자(명)",
                                    fontSize = (containerSize.height * 2/100).toInt().sp
                                )
                            }
                            Button(
                                onClick = {nextScreen()},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("다음 화면")
                            }
                        }
                    }
                }
            } else {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(innerPadding)
                            .onGloballyPositioned { coordinates ->
                                containerSize = coordinates.size.toSize()
                            }
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
                                        fontSize = (containerSize.width * 15/100).toInt().sp,
                                        color = Color(0xff00dd00)
                                    )
                                } else if(timeLeft in 31..90) {
                                    Text(
                                        text = "$timeLeft",
                                        fontSize = (containerSize.width * 15/100).toInt().sp,
                                        color = Color(0xfff8d000)
                                    )
                                } else {
                                    Text(
                                        text = "$timeLeft",
                                        fontSize = (containerSize.width * 15/100).toInt().sp,
                                        color = Color(0xfff00000)
                                    )
                                }
                                Text(
                                    text = "남은 시간(초)",
                                    fontSize = (containerSize.width * 2/100).toInt().sp
                                )
                            }

                            Spacer(modifier = Modifier.height((containerSize.height * 5/100).toInt().dp))

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                if(numberOfCurrentSurvivors/numberOfPlayers.toFloat() > 2/3f) {
                                    Text(
                                        text = "$numberOfCurrentSurvivors/$numberOfPlayers",
                                        fontSize = (containerSize.width * 8/100).toInt().sp,
                                        color = Color(0xff00dd00)
                                    )
                                } else if(numberOfCurrentSurvivors/numberOfPlayers.toFloat() <= 2/3f && numberOfCurrentSurvivors/numberOfPlayers.toFloat() > 1/3f) {
                                    Text(
                                        text = "$numberOfCurrentSurvivors/$numberOfPlayers",
                                        fontSize = (containerSize.width * 8/100).toInt().sp,
                                        color = Color(0xfff8d000)
                                    )
                                } else {
                                    Text(
                                        text = "$numberOfCurrentSurvivors/$numberOfPlayers",
                                        fontSize = (containerSize.width * 8/100).toInt().sp,
                                        color = Color(0xfff00000)
                                    )
                                }
                                Text(
                                    text = "현재 생존자(명)",
                                    fontSize = (containerSize.width * 2/100).toInt().sp
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
            }
        }
    }
}