package com.alonso.testapp.data.impl

import com.alonso.testapp.data.api.ApiService
import com.alonso.testapp.domain.models.AllTraining
import com.alonso.testapp.domain.models.TrainingInterval
import com.alonso.testapp.domain.repo.TrainingRepository

class TrainingRepositoryImpl (
    private val api: ApiService
) : TrainingRepository {
    override var currentTraining: AllTraining? = null

    override suspend fun loadTraining(id: Long): AllTraining {
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

        currentTraining = plan
        return plan
    }
}