package com.example.sharonapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sharonapp.ui.theme.SharonAppTheme
import kotlinx.coroutines.delay

// 완성
class Start {
    companion object {
        @Composable
        fun StartScreen(configuration: Configuration, navController: NavHostController) {
            val screenWidth = configuration.screenWidthDp
            val screenHeight = configuration.screenHeightDp

            LaunchedEffect(Unit) {
                delay(2000)
                navController.navigate("home")
            }

            Scaffold(
                bottomBar = {
                    BottomAppBar (
                        containerColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .height((screenHeight * 8/100).dp)
                            .padding(16.dp)
                    ) {
                        Text(
                            // 넷마블이나 카카오처럼 team 4.5 로고 띄우고 Start할까?
                            text = "by team 4.5",
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            ) { innerPadding ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = "무궁화 꽃이",
                        fontSize = (screenWidth * 15/100).sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "피었습니다!",
                        fontSize = (screenWidth * 15/100).sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun StartScreenPreview() {
//    SharonAppTheme {
//        Start.StartScreen(LocalConfiguration.current, nextScreen = {})
//    }
//}