package com.infinity.apps.magnisetesttask.domain.model.core


import java.util.concurrent.TimeUnit
import java.util.Date

data class TimeRange(
    val startEpochMillis: Long,
    val endEpochMillis: Long
) {
    init {
        require(startEpochMillis <= endEpochMillis) { "Start time must be before end time" }
    }

    fun durationInHours(): Long {
        return TimeUnit.MILLISECONDS.toHours(endEpochMillis - startEpochMillis)
    }

    companion object {
        fun fromDate(start: Date, end: Date): TimeRange {
            return TimeRange(start.time, end.time)
        }

        fun fromEpochMillis(start: Long, end: Long): TimeRange {
            return TimeRange(start, end)
        }
    }
}