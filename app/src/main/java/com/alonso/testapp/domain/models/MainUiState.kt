package com.alonso.testapp.domain.models

data class MainUiState(
    val isLoading: Boolean = false,
    val isLoaded: Boolean = false,
    val trainingId: Long? = null,
    val error: String? = null,
)
