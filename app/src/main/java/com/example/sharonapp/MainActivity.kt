package com.example.sharonapp

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
import com.example.sharonapp.screens.CountdownClass
import com.example.sharonapp.screens.HomeClass
import com.example.sharonapp.screens.InGameClass
import com.example.sharonapp.screens.GameResultClass
import com.example.sharonapp.screens.StartClass
import com.example.sharonapp.screens.TerminationClass
import com.example.sharonapp.screens.WaitingRoomClass
import com.example.sharonapp.ui.theme.SharonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            var idInput by remember { mutableStateOf("") }

            val configuration = LocalConfiguration.current

            SharonTheme {

                var currentScreen by remember { mutableStateOf("StartScreen") }
                // Nav 기능으로 화면 전환하자

                when (currentScreen) {
                    "StartScreen" -> StartClass.StartScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "HomeScreen"
                        }
                    )
                    "HomeScreen" -> idInput = HomeClass.HomeScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "WaitingRoomScreen"
                        }
                    )
                    "WaitingRoomScreen" -> idInput = WaitingRoomClass.WaitingRoomScreen(
idInput,
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "CountdownScreen"
                        }
                    )
                    "CountdownScreen" -> idInput = CountdownClass.CountdownScreen(idInput,
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "InGameScreen"
                        }
                    )
                    "InGameScreen" -> InGameClass.InGameScreen(idInput,
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "TerminationScreen"
                        }
                    )
                    "TerminationScreen" -> TerminationClass.TerminationScreen(
                        configuration = configuration,
                        nextScreen = {
                            currentScreen = "ResultScreen"
                        }
                    )
                    "ResultScreen" -> GameResultClass.ResultScreen(
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
