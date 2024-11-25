package com.example.sharon.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharon.ui.theme.Red
import com.example.sharon.ui.theme.SharonTheme

class WaitingRoom {
    companion object {
        @Composable
        fun WaitingRoomScreen(configuration: Configuration, nextScreen: () -> Unit) {
            val screenWidth = configuration.screenWidthDp
            val screenHeight = configuration.screenHeightDp

            val playerData: List<List<String>>

            val testData = listOf(
                listOf("테스트용1", "999", "false", "true", "false"),
                listOf("테스트용2", "999", "false", "true", "false"),
                listOf("테스트용3", "999", "true", "true", "false"),
                listOf("테스트용4", "999", "false", "true", "false"),
                listOf("테스트용5", "999", "true", "true", "false"),
                listOf("테스트용6", "999", "false", "true", "false"),
                listOf("테스트용7", "999", "true", "true", "false"),
                listOf("테스트용8", "999", "false", "true", "false"),
                listOf("테스트용9", "999", "true", "true", "false")
            )
            playerData = testData

            val numberOfPlayers  by remember { mutableStateOf(playerData.size) }

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
                            .padding(16.dp, 16.dp, 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Test Room",
                            fontSize = (screenWidth * 10/100).sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {nextScreen()},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("준비 완료")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        LazyVerticalGrid(
                            horizontalArrangement = Arrangement.spacedBy((screenWidth * 1/100).dp),
                            verticalArrangement = Arrangement.spacedBy((screenWidth * 1/100).dp),
                            columns = GridCells.Adaptive(minSize = (screenWidth * 30/100).dp)
                        ) {
                            items(numberOfPlayers) { index ->
                                PlayerBox(
                                    width = (screenWidth * 30/100),
                                    index = index,
                                    screenWidth = screenWidth,
                                    playerData = playerData
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        PlayerBox(
                            width = (screenWidth * 60/100),
                            index = 0,
                            screenWidth = screenWidth,
                            playerData = playerData
                            )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
@Composable
fun PlayerBox(width: Int, index: Int, screenWidth: Int, playerData: List<List<String>>) {
    Box(
        modifier = Modifier
            .border(
                width = (screenWidth * 1/100).dp,
                color = Red,
                shape = RoundedCornerShape((screenWidth * 3/100).dp)
            )
            .size(width.dp)
            .aspectRatio(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = playerData[index][0],
                fontSize = (width/6).sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                            .weight(1f)
                            .fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape((width * 128/60).dp, (width * 128/60).dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .weight(2f)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = playerData[index][1],
                            fontSize = (width/6).sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaitingRoomScreenPreview() {
    SharonTheme {
        WaitingRoom.WaitingRoomScreen(LocalConfiguration.current, nextScreen = {})
    }
}