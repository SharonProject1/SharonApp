package com.example.sharonapp.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint.Align
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sharonapp.InGame
import com.example.sharonapp.ui.theme.Green
import com.example.sharonapp.ui.theme.Yellow
import com.example.sharonapp.ui.theme.Red
import com.example.sharonapp.ui.theme.SharonAppTheme
import com.example.sharonapp.ui.theme.White
import kotlinx.coroutines.delay
import kotlin.math.sign
import kotlin.math.sqrt

class InGameClass {
    companion object {
        @SuppressLint("NewApi")
        @Composable
        fun InGameScreen(
            inGame: InGame? = null,
            gameState: List<Int> = listOf(180, 10, 10),
            onNavigateToGameResult: () -> Unit
        ) {
            val screenWidth: Int = LocalConfiguration.current.screenWidthDp
            val screenHeight: Int = LocalConfiguration.current.screenHeightDp

            // 이 부분은 편의에 따라 수정 협의 필요
            val isVoicing by remember { mutableStateOf(false) }
            val timeLeft: Int = gameState[0]
            val numberOfPlayers: Int = gameState[1]
            val numberOfAlivePlayers: Int = gameState[2]

            var isPlayerFinished by remember { mutableStateOf(false) }
            var isGameOver by remember { mutableStateOf(false) }

            val context = LocalContext.current

            val sensorManager = remember { context.getSystemService(SensorManager::class.java) }
            val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

            var xValue by remember { mutableFloatStateOf(0f) }
            var yValue by remember { mutableFloatStateOf(0f) }
            var zValue by remember { mutableFloatStateOf(0f) }
            val threshold = 15f

            val canMove = !isVoicing

            var motionDetected by remember { mutableStateOf(false) } // true 되면 탈락 요청

            var isTagged by remember { mutableStateOf(false) } // true 되면 생존 요청

            var isFailedByTimeOut by remember { mutableStateOf(false) } // true 되면 탈락 요청

            LaunchedEffect(timeLeft) {
                if(timeLeft <= 0 && !isPlayerFinished) {
                    isFailedByTimeOut = true
                    isPlayerFinished = true
                }
            }

            LaunchedEffect(timeLeft, numberOfAlivePlayers) {
                if(timeLeft <= 0 || numberOfAlivePlayers <= 0) {
                    isGameOver = true
                }
            }

            LaunchedEffect(isGameOver) {
                if(isGameOver) {
                    delay(3000)
                    onNavigateToGameResult()
                }
            }

            if (context is Activity) {
                context.intent?.let { intent ->
                    when(intent.action) {
                        NfcAdapter.ACTION_TAG_DISCOVERED -> {
                            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
                            tag?.let {
                                if (!isGameOver) {
                                    isTagged = true
                                    isPlayerFinished = true
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }

            DisposableEffect(Unit) {
                val sensorEventListener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent?) {
                        event?.let {
                            xValue = it.values[0]
                            yValue = it.values[1]
                            zValue = it.values[2]

                            val acceleration = sqrt(xValue * xValue + yValue * yValue + zValue * zValue)

                            if (acceleration > threshold && !canMove && !isPlayerFinished) {
                                motionDetected = true
                                isPlayerFinished = true
                            }
                        }
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
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
                            .padding(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(4f)
                        ) {
                            Spacer(modifier = Modifier.weight(1f))

                            Box(
                                modifier = Modifier
                                    .weight(1.5f)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.DarkGray)
                                    .fillMaxHeight(0.25f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(if(!canMove) Red else Color.Black)
                                            .weight(3f)
                                            .aspectRatio(1f)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(Color.Black)
                                            .weight(3f)
                                            .aspectRatio(1f)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(if(canMove) Green else Color.Black)
                                            .weight(3f)
                                            .aspectRatio(1f)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(0.7f)
                                    .height(5.dp)
                                    .clip(RectangleShape)
                                    .background(Color.DarkGray)
                            )

                            Spacer(modifier = Modifier.weight(0.3f))
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "$timeLeft",
                                fontSize = (screenWidth * 25/100).sp,
                                color = if(timeLeft > 90) {
                                    Green
                                } else if(timeLeft in 31..90) {
                                    Yellow
                                } else {
                                    Red
                                }
                            )
                            Text(
                                text = "남은 시간(초)",
                                fontSize = (screenWidth * 7/100).sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "$numberOfAlivePlayers/$numberOfPlayers",
                                fontSize = (screenWidth * 20/100).sp,
                                color = if(numberOfAlivePlayers/numberOfPlayers.toFloat() > 2/3f) {
                                    Green
                                } else if(numberOfAlivePlayers/numberOfPlayers.toFloat() in 1/3f..2/3f) {
                                    Yellow
                                } else {
                                    Red
                                }
                            )
                            Text(
                                text = "현재 생존자(명)",
                                fontSize = (screenWidth * 7/100).sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(3f))

                        Button(
                            onClick = { onNavigateToGameResult() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("다음 화면")
                        }
                    }
                }
            }

            if(isTagged && isPlayerFinished && !isGameOver) {
                GameOverDialog(
                    cause = "survived",
                    size = screenWidth * 80/100
                )
            } else if(motionDetected && isPlayerFinished && !isGameOver) {
                GameOverDialog(
                    cause = "motionDetected",
                    size = screenWidth * 80/100
                )
            }

            if(isGameOver) {
                Dialog(onDismissRequest = {}) {
                    Text(
                        text = "게임 종료",
                        fontSize = (screenWidth * 20 / 100).sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun GameOverDialog(cause: String, size: Int) {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier
                .width(size.dp)
                .height((size * 8/10).dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color(
                    red = MaterialTheme.colorScheme.onBackground.red,
                    green = MaterialTheme.colorScheme.onBackground.green,
                    blue = MaterialTheme.colorScheme.onBackground.blue,
                    alpha = MaterialTheme.colorScheme.onBackground.alpha * 9/10
                ),
                containerColor = Color(
                red = MaterialTheme.colorScheme.background.red,
                green = MaterialTheme.colorScheme.background.green,
                blue = MaterialTheme.colorScheme.background.blue,
                alpha = MaterialTheme.colorScheme.background.alpha * 9/10
                )
            )
        ) {
            val headerText: String
            val resultMessage: String
            val notificationMessage: String
            val messageColor: Color

            when (cause) {
                "survived" -> {
                    headerText = "축하합니다!"
                    resultMessage = "생존했습니다"
                    notificationMessage = "다른 플레이어들을 기다리는 중..."
                    messageColor = Green
                }
                "motionDetected" -> {
                    headerText = "탕!"
                    resultMessage = "탈락했습니다"
                    notificationMessage = "다른 플레이어들을 기다리는 중..."
                    messageColor = Red
                }
                else -> {
                    headerText = ""
                    resultMessage = "문제가 발생했습니다"
                    notificationMessage = ""
                    messageColor = MaterialTheme.colorScheme.onBackground
                }
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = headerText,
                    fontSize = (if(headerText == "축하합니다!") size * 15/100 else size * 25/100).sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = resultMessage,
                    color = messageColor,
                    fontSize = (size * 5/100).sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = notificationMessage,
                    fontSize = (size * 5/100).sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InGameScreenPreview() {
    val screenWidth: Int = LocalConfiguration.current.screenWidthDp
    val screenHeight: Int = LocalConfiguration.current.screenHeightDp

    SharonAppTheme {
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
                        .padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(4f)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier
                                .weight(1.5f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.DarkGray)
                                .fillMaxHeight(0.25f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(if(true) Red else Color.Black)
                                        .weight(3f)
                                        .aspectRatio(1f)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color.Black)
                                        .weight(3f)
                                        .aspectRatio(1f)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(if(true) Green else Color.Black)
                                        .weight(3f)
                                        .aspectRatio(1f)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(0.7f)
                                .height(5.dp)
                                .clip(RectangleShape)
                                .background(Color.DarkGray)
                        )

                        Spacer(modifier = Modifier.weight(0.3f))
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "60",
                            fontSize = (screenWidth * 25/100).sp,
                            color = Green
                        )
                        Text(
                            text = "남은 시간(초)",
                            fontSize = (screenWidth * 7/100).sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "9/10",
                            fontSize = (screenWidth * 20/100).sp,
                            color = Green
                        )
                        Text(
                            text = "현재 생존자(명)",
                            fontSize = (screenWidth * 7/100).sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(3f))

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("다음 화면")
                    }
                }
            }
        }
        Dialog(onDismissRequest = {}) {
            Text(
                text = "게임 종료",
                fontSize = (screenWidth * 20 / 100).sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}