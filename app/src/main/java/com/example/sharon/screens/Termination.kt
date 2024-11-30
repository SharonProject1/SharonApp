package com.example.sharon.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharon.ui.theme.SharonTheme

class Termination {
    companion object {
        @Composable
        fun TerminationScreen(configuration: Configuration, nextScreen: () -> Unit) {
            val screenWidth = configuration.screenWidthDp
            val screenHeight = configuration.screenHeightDp

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
    }
}

@Preview(showBackground = true)
@Composable
fun TerminationScreenPreview() {
    SharonTheme {
        Termination.TerminationScreen(LocalConfiguration.current, nextScreen = {})
    }
}