package com.ivanksk.pulkovo.stats.models

data class Average(
    val label: String,
    val average: Double,
    val max: Long,
    val min: Long
)