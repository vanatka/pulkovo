package com.ivanksk.pulkovo.stats

import com.ivanksk.pulkovo.operations.IMeasureRecord
import com.ivanksk.pulkovo.stats.models.Average
import com.ivanksk.pulkovo.stats.models.Median

interface IStatsPersistence {

    fun store(record: IMeasureRecord)

    fun allRecords(): List<IMeasureRecord>

    fun allMedians(): List<Median>

    fun allAverage(): List<Average>

    fun clear()

}