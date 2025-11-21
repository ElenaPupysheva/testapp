package com.alonso.testapp.domain.repo

import com.alonso.testapp.domain.models.AllTraining
import kotlinx.coroutines.flow.StateFlow

interface TrainingRepository {
    val currentTraining: StateFlow<AllTraining?>
    suspend fun loadTraining(id: Long)

}