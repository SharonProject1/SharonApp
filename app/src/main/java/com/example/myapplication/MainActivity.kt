package com.example.myapplication

// 필요한 import 문들
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 화면 전환을 위한 상태 변수
            var currentScreen by remember { mutableStateOf("GameReady") }

            // 현재 화면에 따라 다른 컴포저블을 표시
            when (currentScreen) {
                "GameReady" -> GameReadyScreen(onGameReady = {
                    currentScreen = "Countdown"
                })
                "Countdown" -> CountdownScreen(onCountdownEnd = {
                    currentScreen = "NextScreen" // 다음 화면으로 전환
                })
                "NextScreen" -> NextScreen() // 새로운 화면
            }
        }
    }
}
@Composable
fun NextScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "다음 화면입니다!",
            fontSize = 48.sp,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}
@Composable
fun GameReadyScreen(onGameReady: () -> Unit) {
    // UI 구성
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Column을 사용하여 텍스트와 버튼을 세로로 정렬
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("준비 인원")

            Spacer(modifier = Modifier.height(8.dp)) // 텍스트와 버튼 사이 간격 조절

            // 중앙에 배치된 버튼
            Button(
                onClick = { onGameReady() },
                modifier = Modifier.size(200.dp, 50.dp)
            ) {
                Text(text = "게임 준비")
            }
        }
    }
}
@Composable
fun CountdownScreen(onCountdownEnd: () -> Unit) {
    // 카운트다운 숫자를 저장하는 상태 변수
    var count by remember { mutableStateOf(3) }

    // 색상 배열
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta)

    // 카운트다운을 위한 코루틴
    LaunchedEffect(Unit) {
        while (count > -1) {
            delay(1000) // 1초 지연
            count--
        }
        onCountdownEnd()
    }

    // count 값에 따른 색상 선택
    val displayColor = colors[count % colors.size] // count 값에 따라 색상 변경
    val displayText = if (count > 0) "$count" else "시작!"

    // UI 구성
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            fontSize = 300.sp,
            color = displayColor, // 매 초마다 색상이 변경됨
            style = MaterialTheme.typography.displayLarge
        )
    }
}
