package com.example.sharon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.sharon.ui.theme.SharonTheme
import com.example.sharon.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharonTheme {
                var currentScreen by remember { mutableStateOf("StartScreen") }
                // StartScreen - HomeScreen - WaitingRoomScreen - CountdownScreen - InGameScreen - TerminationScreen - ResultScreen
                // Nav 기능으로 화면 전환하자
                when (currentScreen) {
                    "StartScreen" -> Start.StartScreen(nextScreen = {
                        currentScreen = "HomeScreen"
                    })
                    "HomeScreen" -> Home.HomeScreen(nextScreen = {
                        currentScreen = "WaitingRoomScreen"
                    })
                    "WaitingRoomScreen" -> WaitingRoom.WaitingRoomScreen(nextScreen = {
                        currentScreen = "CountdownScreen"
                    })
                    "CountdownScreen" -> Countdown.CountdownScreen(nextScreen = {
                        currentScreen = "InGameScreen"
                    })
                    "InGameScreen" -> InGame.InGameScreen(nextScreen = {
                        currentScreen = "TerminationScreen"
                    })
                    "TerminationScreen" -> Termination.TerminationScreen(nextScreen = {
                        currentScreen = "ResultScreen"
                    })
                    "ResultScreen" -> Result.ResultScreen(nextScreen = {
                        currentScreen = "StartScreen"
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SharonPreview(){
    SharonTheme(darkTheme = true) {
        var currentScreen by remember { mutableStateOf("StartScreen") }

        when (currentScreen) {
            "StartScreen" -> Start.StartScreen(nextScreen = {
                currentScreen = "HomeScreen"
            })
            "HomeScreen" -> Home.HomeScreen(nextScreen = {
                currentScreen = "WaitingRoomScreen"
            })
            "WaitingRoomScreen" -> WaitingRoom.WaitingRoomScreen(nextScreen = {
                currentScreen = "CountdownScreen"
            })
            "CountdownScreen" -> Countdown.CountdownScreen(nextScreen = {
                currentScreen = "InGameScreen"
            })
            "InGameScreen" -> InGame.InGameScreen(nextScreen = {
                currentScreen = "TerminationScreen"
            })
            "TerminationScreen" -> Termination.TerminationScreen(nextScreen = {
                currentScreen = "ResultScreen"
            })
            "ResultScreen" -> Result.ResultScreen(nextScreen = {
                currentScreen = "StartScreen"
            })
        }
    }
}