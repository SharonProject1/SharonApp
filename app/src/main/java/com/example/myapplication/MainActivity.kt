package com.example.myapplication// com.example.myapplication.MainActivity.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Material3 사용 시
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 테마를 적용하려면 여기서 감싸주세요.
            GameReadyScreen()
        }
    }
}

@Composable
fun GameReadyScreen() {
    // 전체 화면을 채우는 박스 레이아웃
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
                onClick = { /* 버튼 클릭 시 동작 */ },
                modifier = Modifier.size(200.dp, 50.dp)
            ) {
                Text(text = "게임 준비")
            }
        }
    }
}
