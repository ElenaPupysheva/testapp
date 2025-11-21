package com.alonso.testapp.domain.repo

import com.alonso.testapp.domain.models.AllTraining

interface TrainingRepository {
    suspend fun loadTraining(id: Long): AllTraining
    var currentTraining: AllTraining?
}