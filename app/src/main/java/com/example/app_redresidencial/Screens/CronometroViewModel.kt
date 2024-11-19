package com.example.app_redresidencial.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CronometroViewModel : ViewModel() {

    var time by mutableStateOf(0L)
        private set

    private var isRunning = false
    private var timerJob: Job? = null

    // Intervalo en milisegundos para controlar la velocidad del cronómetro (default 1000 ms = 1 segundo)
    var interval = 1000L

    fun startTimer() {
        if (!isRunning) {
            isRunning = true
            timerJob = viewModelScope.launch {
                while (isRunning) {
                    delay(interval)
                    time += 1
                }
            }
        }
    }

    fun pauseTimer() {
        isRunning = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        isRunning = false
        timerJob?.cancel()
        time = 0L
    }

    // Función para ajustar la velocidad del cronómetro
    fun setSpeed(speed: Long) {
        interval = speed
    }
}


