package com.example.sharon.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharon.ui.theme.Green
import com.example.sharon.ui.theme.Red
import com.example.sharon.ui.theme.SharonTheme
import com.example.sharon.ui.theme.White

// 추후 삭제 요망
val testData = listOf(
    listOf("나 자신", "NaN", "true", "true", "false"),
    listOf("테스트용1", "1", "false", "true", "false"),
    listOf("테스트용2", "2", "false", "true", "false"),
    listOf("테스트용3", "3", "true", "true", "false"),
    listOf("테스트용4", "4", "false", "true", "false"),
    listOf("테스트용5", "5", "true", "true", "false"),
    listOf("테스트용6", "6", "false", "true", "false"),
    listOf("테스트용7", "7", "true", "true", "false"),
    listOf("테스트용8", "8", "false", "true", "false"),
    listOf("테스트용9", "9", "true", "true", "false")
)

class WaitingRoom {
    companion object {
        @Composable
        fun WaitingRoomScreen(configuration: Configuration, nextScreen: () -> Unit) {
            val screenWidth = configuration.screenWidthDp
            val screenHeight = configuration.screenHeightDp

            val focusManager = LocalFocusManager.current
            val isButtonEnabled = remember { mutableStateOf(false) }
            val isButtonOn = remember { mutableStateOf(false) }

            val playerData: List<List<String>> = testData
            val numberOfPlayers by remember { mutableStateOf(playerData.size) }
            val myIndex: Int = 2// 내 index

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
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
                            items(numberOfPlayers-1) { index ->
                                PlayerBox(
                                    size = cellSize,
                                    index = index+1,
                                    screenWidth = screenWidth,
                                    playerData = playerData
                                )
                                }
                        }
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        PlayerBox(
                            size = (screenHeight * 30/100),
                            index = 0,
                            doesTextFieldExists = true,
                            isButtonEnabled = isButtonEnabled,
                            isButtonOn = isButtonOn,
                            screenWidth = screenWidth,
                            playerData = playerData
                        )
                        Spacer(modifier = Modifier.height((screenHeight * 2/100).dp))
                        Button(
                            onClick = {
                                isButtonOn.value = !isButtonOn.value
                                if(isButtonOn.value)
                                    // GET /ready/:id
                                else
                                    // GET /notReady/:id
                                nextScreen()
                            },
                            enabled = isButtonEnabled.value,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if(isButtonOn.value) Color(0x5000FF00) else Color(0xA000FF00)
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
fun PlayerBox(
    size: Int,
    index: Int,
    doesTextFieldExists: Boolean = false,
    isButtonEnabled: MutableState<Boolean> = mutableStateOf(false),
    isButtonOn: MutableState<Boolean> = mutableStateOf(false),
    screenWidth: Int, playerData: List<List<String>>
) {
    var textState by remember { mutableStateOf("") }

    val isReady = playerData[index][2]
    var figureColor = Red
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
                        if(doesTextFieldExists) {
                            TextField(
                                value = textState,
                                textStyle = TextStyle(
                                    fontSize = (size/6).sp,
                                    lineHeight = (size/4).sp,
                                    color = White,
                                    textAlign = TextAlign.Center
                                ),
                                onValueChange = {
                                    if(it.all { it.isDigit() } && it.length <= 3) {
                                        textState = it
                                        isButtonEnabled.value = it.isNotEmpty()
                                    }
                                },
                                placeholder = {
                                    Text(
                                        text = "번호 입력",
                                        fontSize = (size * 8/100).sp,
                                        lineHeight = (size/4).sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .fillMaxWidth()
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = White,
                                    unfocusedTextColor = Color.LightGray,
                                    disabledTextColor = Color.Gray,
                                    focusedContainerColor = Color(0x00000000),
                                    unfocusedContainerColor = Color(0x00000000),
                                    disabledContainerColor = Color(0x00000000),
                                    focusedIndicatorColor = Color(0x00000000),
                                    unfocusedIndicatorColor = Color(0x00000000),
                                    disabledIndicatorColor = Color(0x00000000),
                                    cursorColor = White
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                enabled = !isButtonOn.value,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
                            )
                        } else {
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
}

@Preview(showBackground = true)
@Composable
fun WaitingRoomScreenPreview() {
    SharonTheme {
        WaitingRoom.WaitingRoomScreen(LocalConfiguration.current, nextScreen = {})
    }
}