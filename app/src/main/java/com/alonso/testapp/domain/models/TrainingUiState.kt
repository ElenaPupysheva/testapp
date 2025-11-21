package com.alonso.testapp.domain.models

import com.alonso.testapp.presentation.TrainingTab


data class TrainingUiState(
    val intervals: List<TrainingInterval> = emptyList(),
    val totalDurationSec: Int = 0,
    val currentIntervalIndex: Int = -1,
    val elapsedInCurrentSec: Int = 0,
    val elapsedTotalSec: Int = 0,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false,
    val selectedTab: TrainingTab = TrainingTab.Timer
)
