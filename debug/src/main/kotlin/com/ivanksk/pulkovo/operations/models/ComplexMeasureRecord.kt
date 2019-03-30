package com.ivanksk.pulkovo.operations.models

import com.ivanksk.pulkovo.operations.IMeasureRecord

data class ComplexMeasureRecord(
    val record: String,
    var duration: Long,
    val timeStamp: Long,
    val optionalPayload: String? = null
) : IMeasureRecord {

    override fun label() = record

    override fun duration() = duration

    override fun timeStamp() = timeStamp

    override fun optionalPayload() = optionalPayload

    override fun toString(): String = "$record took $duration ms at $timeStamp with $optionalPayload payload"

}