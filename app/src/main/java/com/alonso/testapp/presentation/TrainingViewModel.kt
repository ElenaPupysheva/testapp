package com.alonso.testapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alonso.testapp.domain.models.TrainingUiState
import com.alonso.testapp.domain.repo.TrainingRepository
import com.alonso.testapp.utils.sound.SoundPlayer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainingViewModel(
    private val repository: TrainingRepository,
    private val soundPlayer: SoundPlayer
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingUiState())
    val uiState: StateFlow<TrainingUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        val plan = repository.currentTraining
        if (plan != null) {
            _uiState.value = TrainingUiState(
                intervals = plan.intervals,
                totalDurationSec = plan.totalDurationSec
            )
        }
    }

    fun onTabSelected(tab: TrainingTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun onStartStopClick() {
        if (_uiState.value.isRunning) stop() else start()
    }

    private fun start() {
        val intervals = _uiState.value.intervals
        if (intervals.isEmpty()) return

        val state = _uiState.value
        val newIndex = if (state.currentIntervalIndex < 0) 0 else state.currentIntervalIndex

        _uiState.value = state.copy(
            isRunning = true,
            isFinished = false,
            currentIntervalIndex = newIndex
        )
        soundPlayer.beep()

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.isRunning && !_uiState.value.isFinished) {
                delay(1000L)
                tick()
            }
        }
    }

    private fun tick() {
        val state = _uiState.value
        val intervals = state.intervals
        if (intervals.isEmpty()) return

        val currentIndex = if (state.currentIntervalIndex < 0) 0 else state.currentIntervalIndex
        val current = intervals[currentIndex]

        val newElapsedIn = state.elapsedInCurrentSec + 1
        val newElapsedTotal = state.elapsedTotalSec + 1

        if (newElapsedIn >= current.durationSec) {
            val isLast = currentIndex == intervals.lastIndex
            if (isLast) {
                _uiState.value = state.copy(
                    elapsedInCurrentSec = current.durationSec,
                    elapsedTotalSec = newElapsedTotal,
                    isRunning = false,
                    isFinished = true
                )
                viewModelScope.launch {
                    soundPlayer.beep()
                    delay(200L)
                    soundPlayer.beep()
                }
            } else {
                _uiState.value = state.copy(
                    currentIntervalIndex = currentIndex + 1,
                    elapsedInCurrentSec = 0,
                    elapsedTotalSec = newElapsedTotal
                )
                soundPlayer.beep()
            }
        } else {
            _uiState.value = state.copy(
                elapsedInCurrentSec = newElapsedIn,
                elapsedTotalSec = newElapsedTotal
            )
        }
    }

    private fun stop() {
        timerJob?.cancel()
        timerJob = null
        _uiState.value = _uiState.value.copy(isRunning = false)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}