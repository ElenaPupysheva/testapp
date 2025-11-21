package com.alonso.testapp.domain.models

data class MainUiState(
    val isLoading: Boolean = false,
    val isLoaded: Boolean = false,
    val trainingId: Long? = null,
    val navigateToTraining: Boolean = false,
    val error: String? = null,
)
