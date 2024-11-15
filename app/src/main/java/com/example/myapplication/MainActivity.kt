package com.example.myapplication

// 필요한 import 문들

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 화면 전환을 위한 상태 변수
            var currentScreen by remember { mutableStateOf("StScreen") }

            // 현재 화면에 따라 다른 컴포저블을 표시
            when (currentScreen) {
                "StScreen" -> StartScreen(onStartScreen = {
                    currentScreen = "GameReady"
                })
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
fun StartScreen(onStartScreen: () -> Unit)
{
    var codeInput by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp)) // 상단 여백
        Text(
            text = "무궁화꽃이 피었습니다",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(180.dp)) // 텍스트와 입력 칸 사이 여백
        TextField(
            value = codeInput,
            onValueChange = { codeInput = it },
            placeholder = { Text(text = "코드를 입력하세요") },
            modifier = Modifier.fillMaxWidth(0.8f)
            ,keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) // 키보드 타입 설정

        )
        Spacer(modifier = Modifier.height(16.dp)) // 입력 칸과 버튼 사이 여백
        Button(
            onClick = { onStartScreen() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "게임 시작")
        }
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
                modifier = Modifier.size(400.dp, 100.dp)
            ) {
                Text(text = "게임 준비",
                    fontSize = 50.sp)
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



@Composable
fun NextScreen() {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(SensorManager::class.java) }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    var xValue by remember { mutableStateOf(0f) }
    var yValue by remember { mutableStateOf(0f) }
    var zValue by remember { mutableStateOf(0f) }
    var showWarning by remember { mutableStateOf(false) }

    val threshold = 15f // 움직임 감지 기준값 설정

    DisposableEffect(Unit) {
        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    xValue = it.values[0]
                    yValue = it.values[1]
                    zValue = it.values[2]

                    val acceleration = kotlin.math.sqrt(xValue * xValue + yValue * yValue + zValue * zValue)

                    if (acceleration > threshold) {
                        showWarning = true
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // 정확도 변경 시 처리할 내용이 있으면 여기에 작성
            }
        }

        sensorManager.registerListener(
            sensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    Column {
        Text(text = "가속도계 데이터:")
        Text(text = "X축: $xValue")
        Text(text = "Y축: $yValue")
        Text(text = "Z축: $zValue")

        if (showWarning) {
            AlertDialog(
                onDismissRequest = { showWarning = false },
                confirmButton = {
                    Button(onClick = { showWarning = false }) {
                        Text("확인")
                    }
                },
                title = { Text("경고") },
                text = { Text("움직임이 너무 큽니다!") }
            )
        }
    }
}