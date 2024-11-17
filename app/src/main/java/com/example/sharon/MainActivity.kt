package com.example.sharon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharon.ui.theme.SharonTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharonTheme {
                var currentScreen by remember { mutableStateOf("StartScreen") }
                // StartScreen - HomeScreen - WaitingRoomScreen - CountdownScreen - InGameScreen - TerminationScreen - ResultScreen
                // Nav 기능으로 화면 전환하자
                when (currentScreen) {
                    "StartScreen" -> StartScreen(nextScreen = {
                        currentScreen = "HomeScreen"
                    })
                    "HomeScreen" -> HomeScreen(nextScreen = {
                        currentScreen = "WaitingRoomScreen"
                    })
                    "WaitingRoomScreen" -> WaitingRoomScreen(nextScreen = {
                        currentScreen = "CountdownScreen"
                    })
                    "CountdownScreen" -> CountdownScreen(nextScreen = {
                        currentScreen = "InGameScreen"
                    })
                    "InGameScreen" -> InGameScreen(nextScreen = {
                        currentScreen = "TerminationScreen"
                    })
                    "TerminationScreen" -> TerminationScreen(nextScreen = {
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
        Button(
            onClick = {nextScreen()},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("다음 화면")
        }
    }
}

@Composable
fun HomeScreen(nextScreen: () -> Unit) {
    var codeInput by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp)) // 상단 여백
        Text(
            text = "무궁화 꽃이 피었습니다",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(100.dp)) // 텍스트와 입력 칸 사이 여백
        TextField(
            value = codeInput,
            onValueChange = { codeInput = it },
            placeholder = { Text(text = "코드를 입력하세요") },
            modifier = Modifier.fillMaxWidth(0.8f)
            ,keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) // 키보드 타입 설정

        )
        Spacer(modifier = Modifier.height(16.dp)) // 입력 칸과 버튼 사이 여백
        Button(
            onClick = { nextScreen() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "게임 시작")
        }
    }
}

@Composable
fun WaitingRoomScreen(nextScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("대기실 화면")
        Button(
            onClick = {nextScreen()},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("다음 화면")
        }
    }
}

@Composable
fun CountdownScreen(nextScreen: () -> Unit) {
    var count by remember { mutableStateOf(3) }

    val colors = listOf(Color(0xffdd3333), Color(0xff33dd33), Color(0xff3333dd), Color(0xffdd33dd))

    LaunchedEffect(Unit) {
        while (count > -1) {
            delay(1000)
            count--
        }
        nextScreen()
    }

    val displayColor = colors[count % colors.size]
    val displayText = if (count > 0) "$count" else "땡!"

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            fontSize = 300.sp,
            color = displayColor,
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Composable
fun InGameScreen(nextScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("인게임 화면")
        Button(
            onClick = {nextScreen()},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("다음 화면")
        }
    }
}

@Composable
fun TerminationScreen(nextScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("종료 화면(그냥 이거 팝업으로 띄우자)")
        Button(
            onClick = {nextScreen()},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("다음 화면")
        }
    }
}

@Composable
fun ResultScreen(nextScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("결과 화면")
        Button(
            onClick = {nextScreen()},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("홈으로 돌아가기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SharonPreview(){
    SharonTheme {
        var currentScreen by remember { mutableStateOf("StartScreen") }
        // StartScreen - HomeScreen - WaitingRoomScreen - CountdownScreen - InGameScreen - ResultScreen
        // Nav 기능으로 화면 전환하자
        when (currentScreen) {
            "StartScreen" -> StartScreen(nextScreen = {
                currentScreen = "HomeScreen"
            })
            "HomeScreen" -> HomeScreen(nextScreen = {
                currentScreen = "WaitingRoomScreen"
            })
            "WaitingRoomScreen" -> WaitingRoomScreen(nextScreen = {
                currentScreen = "CountdownScreen"
            })
            "CountdownScreen" -> CountdownScreen(nextScreen = {
                currentScreen = "InGameScreen"
            })
            "InGameScreen" -> InGameScreen(nextScreen = {
                currentScreen = "TerminationScreen"
            })
            "TerminationScreen" -> TerminationScreen(nextScreen = {
                currentScreen = "ResultScreen"
            })
            "ResultScreen" -> ResultScreen(nextScreen = {
                currentScreen = "StartScreen"
            })
        }
    }
}