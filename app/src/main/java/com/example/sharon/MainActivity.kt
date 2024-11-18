package com.example.sharon

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.sharon.ui.theme.SharonTheme
import kotlinx.coroutines.delay
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
//        HomeScreen(nextScreen = {})
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