package com.example.sharon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharon.ui.theme.SharonTheme
import com.example.sharon.ui.theme.SharonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharonTheme {
                var currentScreen by remember { mutableStateOf("StartScreen") }
                // StartScreen - HomeScreen - WaitingRoomCScreen - CountdownScreen - InGameScreen - ResultScreen
                // Nav 기능으로 화면 전환하자
                when (currentScreen) {
                    "StartScreen" -> StartScreen(nextScreen = {
                        currentScreen = "HomeScreen"
                    })
                    "HomeScreen" -> HomeScreen(nextScreen = {
                        currentScreen = "WaitingRoomCScreen"
                    })
                    "WaitingRoomCScreen" -> WaitingRoomCScreen(nextScreen = {
                        currentScreen = "CountdownScreen"
                    })
                    "CountdownScreen" -> CountdownScreen(nextScreen = {
                        currentScreen = "InGameScreen"
                    })
                    "InGameScreen" -> InGameScreen(nextScreen = {
                        currentScreen = "ResultScreen"
                    })
                    "ResultScreen" -> ResultScreen(nextScreen = {
                        currentScreen = "StartScreen"
                    })
                }
            }
        }
    }
}

@Composable
fun StartScreen(nextScreen: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            "무궁화 꽃이",
            fontSize = 70.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            "피었습니다!",
            fontSize = 70.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HomeScreen(nextScreen: () -> Unit) {
    Text("홈 화면")
}

@Composable
fun WaitingRoomCScreen(nextScreen: () -> Unit) {
    Text("대기실 화면")
}

@Composable
fun CountdownScreen(nextScreen: () -> Unit) {
    Text("카운트다운 화면")
}

@Composable
fun InGameScreen(nextScreen: () -> Unit) {
    Text("인게임 화면")
}

@Composable
fun TerminationScreen(nextScreen: () -> Unit) {
    Text("종료 화면(그냥 이거 팝업으로 띄우자")
}

@Composable
fun ResultScreen(nextScreen: () -> Unit) {
    Text("결과 화면")
}