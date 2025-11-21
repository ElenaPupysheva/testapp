package com.alonso.testapp.data.impl

import com.alonso.testapp.data.api.ApiService
import com.alonso.testapp.domain.models.AllTraining
import com.alonso.testapp.domain.models.TrainingInterval
import com.alonso.testapp.domain.repo.TrainingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainingRepositoryImpl(
    private val api: ApiService
) : TrainingRepository {

    private val _currentTraining = MutableStateFlow<AllTraining?>(null)
    override val currentTraining: StateFlow<AllTraining?> = _currentTraining.asStateFlow()

    override suspend fun loadTraining(id: Long) {
        val response = api.getIntervalTimer(id)
        val timer = response.timer

        val intervals = timer.intervals.mapIndexed { index, dto ->
            TrainingInterval(
                id = index.toLong(),
                title = dto.title,
                durationSec = dto.time
            )
        }

        val plan = AllTraining(
            id = timer.timerId,
            title = timer.title,
            totalDurationSec = timer.totalTime,
            intervals = intervals
        )

        _currentTraining.value = plan
    }
}