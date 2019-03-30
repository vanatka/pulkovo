package com.ivanksk.pulkovo.dispatcher

import com.ivanksk.pulkovo.operations.IMeasureRecord
import com.ivanksk.pulkovo.reducer.IReducer
import java.util.*

object PulkovoDispatcher : IPulkovoDispatcher {

    private val reducers = Stack<IReducer>()

    override fun addReducer(reducer: IReducer) = apply {
        reducers.add(reducer)
    }

    override fun removeReducer(reducer: IReducer) {
        reducers.remove(reducer)
    }

    override fun processMeasurement(record: IMeasureRecord) {
        reducers.forEach {
            it.reduce(record)
        }
    }

}