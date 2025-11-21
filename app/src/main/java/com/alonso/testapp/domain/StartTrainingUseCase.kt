package com.alonso.testapp.domain

import com.alonso.testapp.domain.models.AllTraining
import com.alonso.testapp.domain.repo.TrainingRepository

class StartTrainingUseCase(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke(id: Long): AllTraining {
        return repository.loadTraining(id)
    }
}