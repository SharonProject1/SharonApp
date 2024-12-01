package com.example.sharonapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharonapp.GameResult
import com.example.sharonapp.R
import com.example.sharonapp.ui.theme.Green
import com.example.sharonapp.ui.theme.Red
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

            val apiService = remember { createApiService() }
            var gameResultData by remember { mutableStateOf(listOf<List<String>>()) }
            var gameResultDataResponded by remember { mutableStateOf(ServerResponse(data = listOf())) }

            LaunchedEffect(Unit) {
                try {
                    withContext(Dispatchers.IO) {
                        gameResultDataResponded = apiService.getPlayerState()
                    }
                    gameResultData = gameResultDataResponded.data
                }
                catch (e: Exception) {
                    val tempData = listOf(
                        listOf("Survived", "1", "11", "None", "-"),
                        listOf("Survived", "2", "22", "None", "-"),
                        listOf("Failed", "3", "33", "None", "-"),
                        listOf("Failed", "4", "44", "None", "-"),
                        listOf("Failed", "5", "55", "None", "-"),
                        listOf("Disconnected", "0", "66", "None", "-")
                    )
                    gameResultData = tempData
                }
            }

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

                        if(gameResultData.isNotEmpty()) {
                            val pagerState = rememberPagerState(
                                pageCount = { gameResultData.size },
                                initialPage = gameResultData.indexOfFirst { it[3] == userId }
                            )
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
                                            color = if(gameResultData[page][0] == "Survived") Green else Red
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
                                            painter = painterResource(id = R.drawable.back),
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
                                                fontSize = if(gameResultData[page][2].length == 3) (screenWidth * 15/100).sp else (screenWidth * 20/100).sp
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
