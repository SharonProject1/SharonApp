package com.example.sharonapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharonapp.GameResult
import com.example.sharonapp.R
import com.example.sharonapp.ui.theme.Green
import com.example.sharonapp.ui.theme.Red
import com.example.sharonapp.ui.theme.SharonAppTheme
import com.example.sharonapp.utility.ServerResponse
import com.example.sharonapp.utility.createApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameResultClass {
    companion object {

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        fun GameResultScreen(
            gameResult: GameResult,
            onNavigateToHome: () -> Unit
        ) {
            val screenWidth: Int = LocalConfiguration.current.screenWidthDp
            val screenHeight: Int = LocalConfiguration.current.screenHeightDp

            val userId = gameResult.userId

            var gameResultData by remember { mutableStateOf(listOf<List<String>>()) }
            val apiService = remember { createApiService() }
            var playerDataResponsed by remember { mutableStateOf(ServerResponse(data = listOf())) }

            LaunchedEffect(Unit) {
                try {
                    withContext(Dispatchers.IO)
                    {
                        playerDataResponsed = apiService.getPlayerState()
                    }
                    gameResultData = playerDataResponsed.data
                }
                catch (e: Exception)
                {
                    println("${e.message} 냐옹")
                }

            }
/* data format
[
    [playerState, rank, playerNumber, playerId, playTime],
    [playerState, ...],
    ...
]
*/

            /* 테스트 데이터 val testResult = listOf( // 삭제 바람
                listOf("생존", "1위", "39", "엄준식", "46s"),
                listOf("생존", "2위", "71", "똥파리", "53초"),
                listOf("탈락", "3위", "3", "아이시떼루", "시간 초과"),
                listOf("탈락", "4위", "666", "마님", "시간 초과"),
                listOf("탈락", "5위", "15", "코파고카레먹기", "35초"),
                listOf("연결 끊김", "-", "39", "돌쇠", "-"),
            )

            gameResultData = testResult */

            val pagerState = rememberPagerState(pageCount = { gameResultData.size })

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "게임 결과",
                            fontSize = (screenWidth * 10/100).sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
//                                .aspectRatio(0.75f)
                        ) { page ->
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        if(page > 0) {
                                            Text(
                                                text = "<",
                                                fontSize = (screenWidth * 10/100).sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                    Text(
                                        text = gameResultData[page][0],
                                        fontSize = (screenWidth * 10/100).sp,
                                        color = if(gameResultData[page][0] == "생존") Green else Red
                                    )
                                    Row(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        if (page < gameResultData.size-1) {
                                            Text(
                                                text = ">",
                                                fontSize = (screenWidth * 10 / 100).sp
                                            )
                                        }
                                    }
                                }
                                Box {
                                    Image(
                                        painter = painterResource(id = R.drawable.test_image),
                                        contentDescription = "test image",
                                        modifier = Modifier.size((screenWidth * 80/100).dp)
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.align(Alignment.Center)
                                    ) {
                                        Text(
                                            text = gameResultData[page][3],
                                            fontSize = (screenWidth * 7/100).sp
                                        )
                                        Text(
                                            text = gameResultData[page][2],
                                            fontSize = (screenWidth * 20/100).sp
                                        )
                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = gameResultData[page][1],
                                            fontSize = (screenWidth * 10/100).sp,
                                            lineHeight = (screenHeight * 8/100).sp
                                        )
                                        Text("순위")
                                    }
                                    Spacer(Modifier.width((screenWidth * 10/100).dp))
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = gameResultData[page][4],
                                            fontSize = (screenWidth * 10/100).sp,
                                            lineHeight = (screenHeight * 8/100).sp
                                        )
                                        Text("플레이 시간")
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { onNavigateToHome() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("홈으로 돌아가기")
                        }

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ResultScreenPreview() {
//    SharonAppTheme {
//        GameResultClass.GameResultScreen(onNavigateToHome = {})
//    }
//}
