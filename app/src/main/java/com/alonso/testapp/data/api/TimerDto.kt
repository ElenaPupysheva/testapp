package com.alonso.testapp.data.api
import com.google.gson.annotations.SerializedName
data class TimerResponseDto(
    val timer: TimerDto
)
data class TimerDto(
    @SerializedName("timer_id")
    val timerId: Long,
    val title: String,
    @SerializedName("total_time")
    val totalTime: Int,
    val intervals: List<IntervalDto>
)
data class IntervalDto(
    val title: String,
    val time: Int
)