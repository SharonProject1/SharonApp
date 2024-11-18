package com.example.sharon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.sharon.ui.theme.SharonTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharonTheme {
                var currentScreen by remember { mutableStateOf("StartScreen") }
                // StartScreen - HomeScreen - WaitingRoomScreen - CountdownScreen - InGameScreen - TerminationScreen - ResultScreen
                // Nav 기능으로 화면 전환하자
                when (currentScreen) {
                    "StartScreen" -> StartScreen(nextScreen = {
                        currentScreen = "HomeScreen"
                    })
                    "HomeScreen" -> HomeScreen(nextScreen = {
                        currentScreen = "WaitingRoomScreen"
                    })
                    "WaitingRoomScreen" -> WaitingRoomScreen(nextScreen = {
                        currentScreen = "CountdownScreen"
                    })
                    "CountdownScreen" -> CountdownScreen(nextScreen = {
                        currentScreen = "InGameScreen"
                    })
                    "InGameScreen" -> InGameScreen(nextScreen = {
                        currentScreen = "TerminationScreen"
                    })
                    "TerminationScreen" -> TerminationScreen(nextScreen = {
                        currentScreen = "ResultScreen"
                    })
                    "ResultScreen" -> ResultScreen(nextScreen = {
                        currentScreen = "StartScreen"
                    })
                }
            }
        }
    }
}

//ThemedBox, ThemedText등 만들어서 디자인 통일하자

@Composable
fun StartScreen(nextScreen: () -> Unit) {
    var containerSize by remember { mutableStateOf(Size.Zero) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        containerSize = coordinates.size.toSize()
                    }
            ) {
                Text(
                    "무궁화 꽃이",
                    fontSize = (containerSize.width * 5/100).toInt().sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    "피었습니다!",
                    fontSize = (containerSize.width * 5/100).toInt().sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {nextScreen()},
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
}

@Composable
fun HomeScreen(nextScreen: () -> Unit) {
    var containerSize by remember { mutableStateOf(Size.Zero) }
    var codeInput by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        containerSize = coordinates.size.toSize()
                    }
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "무궁화 꽃이",
                    fontSize = (containerSize.width * 5/100).toInt().sp
                )
                Text(
                    text = "피었습니다",
                    fontSize = (containerSize.width * 5/100).toInt().sp
                )
                Spacer(modifier = Modifier.height((containerSize.height * 5/100).toInt().dp))
                TextField(
                    value = codeInput,
                    onValueChange = { codeInput = it },
                    placeholder = { Text(text = "코드를 입력하세요") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { nextScreen() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "게임 시작")
                }
            }
        }
    }
}

@Composable
fun WaitingRoomScreen(nextScreen: () -> Unit) {
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("대기실 화면")
                Button(
                    onClick = {nextScreen()},
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
}

@Composable
fun CountdownScreen(nextScreen: () -> Unit) {
    var count by remember { mutableStateOf(3) }

    LaunchedEffect(Unit) {
        while (count > -1) {
            delay(1000)
            count--
        }
        nextScreen()
    }

    val displayText = if (count > 0) "$count" else "땡!"
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayText,
                    fontSize = 300.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    }
}

@Composable
fun InGameScreen(nextScreen: () -> Unit) {
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("인게임 화면")
                Button(
                    onClick = {nextScreen()},
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
}

@Composable
fun TerminationScreen(nextScreen: () -> Unit) {
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("종료 화면(그냥 이거 팝업으로 띄우자)")
                Button(
                    onClick = {nextScreen()},
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
}

@Composable
fun ResultScreen(nextScreen: () -> Unit) {
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("결과 화면")
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

@Preview(showBackground = true)
@Composable
fun SharonPreview(){
    SharonTheme {
        var currentScreen by remember { mutableStateOf("StartScreen") }

        when (currentScreen) {
            "StartScreen" -> StartScreen(nextScreen = {
                currentScreen = "HomeScreen"
            })
            "HomeScreen" -> HomeScreen(nextScreen = {
                currentScreen = "WaitingRoomScreen"
            })
            "WaitingRoomScreen" -> WaitingRoomScreen(nextScreen = {
                currentScreen = "CountdownScreen"
            })
            "CountdownScreen" -> CountdownScreen(nextScreen = {
                currentScreen = "InGameScreen"
            })
            "InGameScreen" -> InGameScreen(nextScreen = {
                currentScreen = "TerminationScreen"
            })
            "TerminationScreen" -> TerminationScreen(nextScreen = {
                currentScreen = "ResultScreen"
            })
            "ResultScreen" -> ResultScreen(nextScreen = {
                currentScreen = "StartScreen"
            })
        }
    }
}