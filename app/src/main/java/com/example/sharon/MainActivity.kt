package com.example.sharon

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharon.ui.theme.SharonTheme
import com.example.sharon.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {

            val configuration = LocalConfiguration.current

            SharonTheme {

                var currentScreen by remember { mutableStateOf("StartScreen") }
                // StartScreen - HomeScreen - WaitingRoomScreen - CountdownScreen - InGameScreen - TerminationScreen - ResultScreen
                // Nav 기능으로 화면 전환하자
                when (currentScreen) {
                    "StartScreen" -> Start.StartScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "HomeScreen"
                        }
                    )
                    "HomeScreen" -> Home.HomeScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "WaitingRoomScreen"
                        }
                    )
                    "WaitingRoomScreen" -> WaitingRoom.WaitingRoomScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "CountdownScreen"
                        }
                    )
                    "CountdownScreen" -> Countdown.CountdownScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "InGameScreen"
                        }
                    )
                    "InGameScreen" -> InGame.InGameScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "TerminationScreen"
                        }
                    )
                    "TerminationScreen" -> Termination.TerminationScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "ResultScreen"
                        }
                    )
                    "ResultScreen" -> Result.ResultScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "StartScreen"
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SharonPreview(){
    val configuration = LocalConfiguration.current

    SharonTheme {
        var currentScreen by remember { mutableStateOf("StartScreen") }
        // StartScreen - HomeScreen - WaitingRoomScreen - CountdownScreen - InGameScreen - TerminationScreen - ResultScreen
        // Nav 기능으로 화면 전환하자
        when (currentScreen) {
            "StartScreen" -> Start.StartScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "HomeScreen"
                }
            )
            "HomeScreen" -> Home.HomeScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "WaitingRoomScreen"
                }
            )
            "WaitingRoomScreen" -> WaitingRoom.WaitingRoomScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "CountdownScreen"
                }
            )
            "CountdownScreen" -> Countdown.CountdownScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "InGameScreen"
                }
            )
            "InGameScreen" -> InGame.InGameScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "TerminationScreen"
                }
            )
            "TerminationScreen" -> Termination.TerminationScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "ResultScreen"
                }
            )
            "ResultScreen" -> Result.ResultScreen(
                configuration = configuration,
                nextScreen = {
                    currentScreen = "StartScreen"
                }
            )
        }
    }
}