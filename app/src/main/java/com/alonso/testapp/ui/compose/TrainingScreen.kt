package com.alonso.testapp.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alonso.testapp.data.api.TimerDto

@Composable
fun TrainingScreen(timer: TimerDto) {
    Text(text = "Тренировка: ${timer.title}, всего секунд: ${timer.totalTime}")
}