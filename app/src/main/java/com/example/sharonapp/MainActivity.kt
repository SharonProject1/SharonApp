package com.example.sharonapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.sharonapp.screens.CountdownClass
import com.example.sharonapp.screens.HomeClass
import com.example.sharonapp.screens.InGameClass
import com.example.sharonapp.screens.GameResultClass
import com.example.sharonapp.screens.StartClass
import com.example.sharonapp.screens.TerminationClass
import com.example.sharonapp.screens.WaitingRoomClass
import com.example.sharonapp.ui.theme.SharonAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            var userId by remember { mutableStateOf("") }

            val configuration = LocalConfiguration.current

            SharonAppTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = WaitingRoom) {
                    composable<Start> {
                        StartClass.StartScreen(
                            configuration = configuration,
                            onNavigateToHome = {
                                navController.navigate(route = Home)
                            }
                        )
                    }
                    composable<Home> {
                        HomeClass.HomeScreen(
                            configuration = configuration,
                            onNavigateToWaitingRoom = { idInput ->
                                userId = idInput
                                navController.navigate(
                                    route = WaitingRoom(userId = userId)
                                )
                            }
                        )
                    }
                    composable<WaitingRoom> { navBackStackEntry ->
                        val waitingRoom: WaitingRoom = navBackStackEntry.toRoute()
                        WaitingRoomClass.WaitingRoomScreen(
                            waitingRoom = waitingRoom,
                            configuration = configuration,
                            onNavigateToCountdown = {
                                navController.navigate(route = Countdown)
                            }
                        )
                    }
                    composable<Countdown> {
                        CountdownClass.CountdownScreen(
                            configuration = configuration,
                            onNavigateToInGame = {
                                navController.navigate(
                                    route = InGame(userId = userId)
                                )
                            }
                        )
                    }
                    composable<InGame> { navBackStackEntry ->
                        val inGame: InGame = navBackStackEntry.toRoute()
                        InGameClass.InGameScreen(
                            inGame = inGame,
                            configuration = configuration,
                            onNavigateToTermination = {
                                navController.navigate(
                                    route = Termination(userId = userId)
                                )
                            }
                        )
                    }
                    composable<Termination> { navBackStackEntry ->
                        val termination: Termination = navBackStackEntry.toRoute()
                        TerminationClass.TerminationScreen(
                            termination = termination,
                            configuration = configuration,
                            onNavigateToResult = {
                                navController.navigate(
                                    route = GameResult(userId = userId)
                                )
                            }
                        )
                    }
                    composable<GameResult> { navBackStackEntry ->
                        val gameResult: GameResult = navBackStackEntry.toRoute()
                        GameResultClass.GameResultScreen(
                            gameResult = gameResult,
                            configuration = configuration,
                            onNavigateToHome = {
                                navController.navigate(route = Home)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Serializable
object Start

@Serializable
object Home

@Serializable
data class WaitingRoom(val userId: String)

@Serializable
object Countdown

@Serializable
data class InGame(val userId: String)

@Serializable
data class Termination(val userId: String)

@Serializable
data class GameResult(val userId: String)

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