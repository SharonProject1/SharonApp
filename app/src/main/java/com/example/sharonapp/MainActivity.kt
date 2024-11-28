package com.example.sharonapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharonapp.screens.Countdown
import com.example.sharonapp.screens.Home
import com.example.sharonapp.screens.InGame
import com.example.sharonapp.screens.Result
import com.example.sharonapp.screens.Start
import com.example.sharonapp.screens.Termination
import com.example.sharonapp.screens.WaitingRoom
import com.example.sharonapp.ui.theme.SharonAppTheme
import kotlin.contracts.contract

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            var idInput by remember { mutableStateOf("") } // 이거 왜 var?

            val configuration = LocalConfiguration.current

            SharonAppTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "start") {
                    composable("start") {
                        Start.StartScreen(
                            configuration = configuration,
                            navController = navController
                        )
                    }
                    composable("home") {
                        Home.HomeScreen(
                            configuration = configuration,
                            navController = navController
                        )
                    }
                    composable("waitingRoom") {
                        WaitingRoom.WaitingRoomScreen(
                            idInput,
                            configuration = configuration,
                            navController = navController
                        )
                    }
                    composable("inGame") {
                        InGame.InGameScreen(
                            configuration = configuration,
                            navController = navController
                        )
                    }
                    composable("termination") {
                        Termination.TerminationScreen(
                            configuration = configuration,
                            navController = navController
                        )
                    }
                    composable("result") {
                        Result.ResultScreen(
                            configuration = configuration,
                            navController = navController
                        )
                    }
                    composable("countdown") {
                        Countdown.CountdownScreen(
                            configuration = configuration,
                            navController = navController
                        )
                    }
                }

//                var currentScreen by remember { mutableStateOf("StartScreen") }
//                // Nav 기능으로 화면 전환하자
//
//                when (currentScreen) {
//                    "StartScreen" -> Start.StartScreen(
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "HomeScreen"
//                        }
//                    )
//                    "HomeScreen" -> idInput = Home.HomeScreen(
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "WaitingRoomScreen"
//                        }
//                    )
//                    "WaitingRoomScreen" -> WaitingRoom.WaitingRoomScreen(
//                        idInput,
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "CountdownScreen"
//
//                        }
//                    )
//                    "CountdownScreen" -> Countdown.CountdownScreen(
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "InGameScreen"
//                        }
//                    )
//                    "InGameScreen" -> InGame.InGameScreen(
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "TerminationScreen"
//                        }
//                    )
//                    "TerminationScreen" -> Termination.TerminationScreen(
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "ResultScreen"
//                        }
//                    )
//                    "ResultScreen" -> Result.ResultScreen(
//                        configuration = configuration,
//                        nextScreen = {
//                            currentScreen = "StartScreen"
//                        }
//                    )
//                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun SharonAppPreview(){
//    val configuration = LocalConfiguration.current
//    var idInput by remember { mutableStateOf("") }
//
//    SharonAppTheme {
//
//        var currentScreen by remember { mutableStateOf("StartScreen") }
//        // Nav 기능으로 화면 전환하자
//
//        when (currentScreen) {
//            "StartScreen" -> Start.StartScreen(
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "HomeScreen"
//                }
//            )
//            "HomeScreen" -> idInput = Home.HomeScreen(
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "WaitingRoomScreen"
//                }
//            )
//            "WaitingRoomScreen" -> WaitingRoom.WaitingRoomScreen(
//                idInput,
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "CountdownScreen"
//
//                }
//            )
//            "CountdownScreen" -> Countdown.CountdownScreen(
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "InGameScreen"
//                }
//            )
//            "InGameScreen" -> InGame.InGameScreen(
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "TerminationScreen"
//                }
//            )
//            "TerminationScreen" -> Termination.TerminationScreen(
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "ResultScreen"
//                }
//            )
//            "ResultScreen" -> Result.ResultScreen(
//                configuration = configuration,
//                nextScreen = {
//                    currentScreen = "StartScreen"
//                }
//            )
//        }
//    }}