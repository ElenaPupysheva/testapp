package com.alonso.testapp.domain.models

data class AllTraining(
    val id: Long,
    val title: String,
    val totalDurationSec: Int,
    val intervals: List<TrainingInterval>
)
