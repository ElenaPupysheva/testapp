package com.alonso.testapp.domain

import com.alonso.testapp.domain.repo.TrainingRepository

class StartTrainingUseCase(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.loadTraining(id)
    }
}