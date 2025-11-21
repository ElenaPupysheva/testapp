package com.alonso.testapp.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.alonso.testapp.data.api.TimerDto
import com.alonso.testapp.presentation.TrainingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainingScreen(
    navController: NavHostController,
    viewModel: TrainingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Text(
        text = "Интервалов: ${uiState.intervals.size}, всего секунд: ${uiState.totalDurationSec}"
    )
}