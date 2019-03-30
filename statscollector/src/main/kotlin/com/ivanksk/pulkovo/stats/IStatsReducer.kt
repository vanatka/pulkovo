package com.ivanksk.pulkovo.stats

import com.ivanksk.pulkovo.operations.IMeasureRecord
import com.ivanksk.pulkovo.reducer.IReducer

interface IStatsReducer: IReducer, IStatsPersistence {

    override fun reduce(record: IMeasureRecord) {
        store(record)
    }

}