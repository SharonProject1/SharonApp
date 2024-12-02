package com.example.sharonapp

import com.example.sharonapp.utility.SharonViewModel
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.sharonapp.screens.CountdownClass
import com.example.sharonapp.screens.HomeClass
import com.example.sharonapp.screens.InGameClass
import com.example.sharonapp.screens.GameResultClass
import com.example.sharonapp.screens.StartClass
import com.example.sharonapp.screens.WaitingRoomClass
import com.example.sharonapp.ui.theme.SharonAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    private val sharonViewModel: SharonViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {

            SharonAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Start) {
                    composable<Start> {
                        StartClass.StartScreen(
                            onNavigateToHome = {
                                navController.navigate(route = Home)
                            }
                        )
                    }
                    composable<Home> {
                        HomeClass.HomeScreen(
                            sharonViewModel = sharonViewModel,
                            onNavigateToWaitingRoom = {
                                navController.navigate(
                                    route = WaitingRoom(userId = sharonViewModel.userId.value)
                                )
                            }
                        )
                    }
                    composable<WaitingRoom> { navBackStackEntry ->
                        val waitingRoom: WaitingRoom = navBackStackEntry.toRoute()
                        WaitingRoomClass.WaitingRoomScreen(
                            waitingRoom = waitingRoom,
                            onNavigateToCountdown = {
                                navController.navigate(route = Countdown)
                            },
                            onNavigateToBack = { navController.navigateUp() }
                        )
                    }
                    composable<Countdown> {
                        CountdownClass.CountdownScreen(
                            onNavigateToInGame = {
                                navController.navigate(
                                    route = InGame(userId = sharonViewModel.userId.value)
                                )
                            }
                        )
                    }
                    composable<InGame> { navBackStackEntry ->
                        val inGame: InGame = navBackStackEntry.toRoute()
                        InGameClass.InGameScreen(
                            inGame = inGame,
                            sharonViewModel = sharonViewModel,
                            onNavigateToGameResult = {
                                navController.navigate(
                                    route = GameResult(userId = sharonViewModel.userId.value)
                                )
                            }
                        )
                    }
                    composable<GameResult> { navBackStackEntry ->
                        val gameResult: GameResult = navBackStackEntry.toRoute()
                        GameResultClass.GameResultScreen(
                            gameResult = gameResult,
                            sharonViewModel = sharonViewModel,
                            onNavigateToHome = {
                                navController.navigate(
                                    route = Home
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        InGameClass.handleNewIntent(intent, sharonViewModel)
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
data class GameResult(val userId: String)