package com.example.app_redresidencial.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun PantallaCronometro(
    navController: NavHostController,
    cronometroViewModel: CronometroViewModel = viewModel()
) {
    val time by cronometroViewModel::time
    val minutes = time / 60
    val seconds = time % 60

    BaseScreen(title = "Cronómetro", navController = navController) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Button(onClick = { cronometroViewModel.startTimer() }) {
                        Text(text = "Iniciar")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { cronometroViewModel.pauseTimer() }) {
                        Text(text = "Pausar")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { cronometroViewModel.resetTimer() }) {
                        Text(text = "Reiniciar")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Button(onClick = { cronometroViewModel.setSpeed(2000L) }) {
                        Text(text = "Lento") //2seg
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(onClick = { cronometroViewModel.setSpeed(1000L) }) {
                        Text(text = "Normal") //1seg
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(onClick = { cronometroViewModel.setSpeed(500L) }) {
                        Text(text = "Rápido") //0.5 Seg
                    }
                }
            }
        }
    }
}


