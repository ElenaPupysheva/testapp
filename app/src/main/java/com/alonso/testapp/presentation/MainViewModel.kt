package com.alonso.testapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alonso.testapp.domain.StartTrainingUseCase
import com.alonso.testapp.domain.models.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val startTrainingUseCase: StartTrainingUseCase
) : ViewModel() {
        private val _uiState = MutableStateFlow(MainUiState())
        val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

        fun loadTraining(id: Long) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                isLoaded = false
            )

            runCatching {
                startTrainingUseCase(id)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoaded = true,
                    trainingId = id
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoaded = false,
                    error = throwable.message
                )
            }
        }
    }
}