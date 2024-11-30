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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharonapp.R
import com.example.sharonapp.ui.theme.Green
import com.example.sharonapp.ui.theme.Red

// 완성
class GameResultClass {
    companion object {

        @OptIn(ExperimentalFoundationApi::class)
        @Composable
        fun ResultScreen(configuration: Configuration, nextScreen: () -> Unit) {
            val screenWidth: Int = configuration.screenWidthDp
            val screenHeight: Int = configuration.screenHeightDp

            val gameResult: List<List<String>> //json으로 받아온 데이터

/* data format
[
    [playerState, rank, playerNumer, playerId, playTime],
    [playerState, ...],
    ...
]
*/

            val testResult = listOf( // 삭제 바람
                listOf("생존", "1위", "39", "엄준식", "46s"),
                listOf("생존", "2위", "71", "똥파리", "53초"),
                listOf("탈락", "3위", "3", "아이시떼루", "시간 초과"),
                listOf("탈락", "4위", "666", "마님", "시간 초과"),
                listOf("탈락", "5위", "15", "코파고카레먹기", "35초"),
                listOf("연결 끊김", "-", "39", "돌쇠", "-"),
            )

            gameResult = testResult

            val pagerState = rememberPagerState(pageCount = { gameResult.size })

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height((screenHeight * 5/100).dp))

                        Text(
                            text = "게임 결과",
                            fontSize = (screenWidth * 10/100).sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height((screenHeight * 10/100).dp))

                        HorizontalPager(state = pagerState) { page ->
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val colorOfPlayerStateText: Color
                                val playerStateText: String = gameResult[page][0]

                                if(playerStateText == "생존")
                                    colorOfPlayerStateText = Green
                                else
                                    colorOfPlayerStateText = Red

                                Text(
                                    text = playerStateText,
                                    fontSize = (screenWidth * 10/100).sp,
                                    color = colorOfPlayerStateText
                                )
                                Spacer(modifier = Modifier.height((screenWidth * 5/100).dp))
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
                                            text = gameResult[page][3],
                                            fontSize = (screenWidth * 7/100).sp
                                        )
                                        Text(
                                            text = gameResult[page][2],
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
                                            text = gameResult[page][1],
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
                                            text = gameResult[page][4],
                                            fontSize = (screenWidth * 10/100).sp,
                                            lineHeight = (screenHeight * 8/100).sp
                                        )
                                        Text("플레이 시간")
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height((screenHeight * 5/100).dp))
                        Button(
                            onClick = {nextScreen()},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("홈으로 돌아가기")
                        }
                    }
                }
            }
        }
    }
}
