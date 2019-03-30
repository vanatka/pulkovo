package com.ivanksk.pulkovo.reducer

import com.ivanksk.pulkovo.operations.IMeasureRecord

interface IReducer {

    fun reduce(record: IMeasureRecord)

}