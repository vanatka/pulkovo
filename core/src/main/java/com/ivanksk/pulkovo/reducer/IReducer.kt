package com.ivanksk.pulkovo.reducer

import com.ivanksk.pulkovo.operations.IMeasureRecord
import kotlin.system.measureTimeMillis

interface IReducer {

    fun reduce(record: IMeasureRecord) {
        measureTimeMillis {  }
    }

}