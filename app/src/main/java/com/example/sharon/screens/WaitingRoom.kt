package com.example.sharon.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharon.ui.theme.Green
import com.example.sharon.ui.theme.Red
import com.example.sharon.ui.theme.SharonTheme

// 추후 삭제 요망
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

class WaitingRoom {
    companion object {
        @Composable
        fun WaitingRoomScreen(configuration: Configuration, nextScreen: () -> Unit) {
            val screenWidth = configuration.screenWidthDp
            val screenHeight = configuration.screenHeightDp

            val playerData: List<List<String>> = testData

            val numberOfPlayers by remember { mutableStateOf(playerData.size) }

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        Text(
                            text = "Test Room",
                            fontSize = (screenWidth * 10/100).sp
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))

                        val cellSize = screenWidth * 30/100
                        LazyVerticalGrid(
                            horizontalArrangement = Arrangement.spacedBy((screenWidth * 2/100).dp, Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy((screenWidth * 2/100).dp, Alignment.CenterVertically),
                            columns = GridCells.FixedSize(cellSize.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(numberOfPlayers) { index ->
                                PlayerBox(
                                    size = cellSize,
                                    index = index,
                                    screenWidth = screenWidth,
                                    playerData = playerData
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        PlayerBox(
                            size = (screenHeight * 30/100),
                            index = 0,
                            screenWidth = screenWidth,
                            playerData = playerData
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        Button(
                            onClick = { nextScreen() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("준비 완료")
                        }
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerBox(size: Int, index: Int, screenWidth: Int, playerData: List<List<String>>) {

    var figureColor = Red

    val isReady = playerData[index][2]
    if(isReady == "true")
        figureColor = Green

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape((screenWidth * 5/100).dp))
            .background(color = Color.DarkGray)
            .size(size.dp)
            .aspectRatio(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = (screenWidth * 2/100).dp, bottom = (screenWidth * 2/100).dp)
                .fillMaxSize()
        ) {
            Text(
                text = playerData[index][0],
                fontSize = (size/6).sp,
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
                            .clip(CircleShape)
                            .background(figureColor)
                            .weight(1f)
                            .aspectRatio(1f)
                            .fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape((size * 64/30).dp, (size * 64/30).dp))
                            .background(figureColor)
                            .weight(2f)
                            .aspectRatio(1f)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = playerData[index][1],
                            fontSize = (size/6).sp,
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