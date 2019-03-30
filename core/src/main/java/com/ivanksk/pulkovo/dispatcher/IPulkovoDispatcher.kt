package com.ivanksk.pulkovo.dispatcher

import com.ivanksk.pulkovo.operations.IMeasureRecord
import com.ivanksk.pulkovo.reducer.IReducer

interface IPulkovoDispatcher {

    /**
     * That method is used to process measurement label
     *
     * @param - completed measurement label will be processed by reducers
     */
    fun processMeasurement(record: IMeasureRecord)

    fun addReducer(reducer: IReducer) : IPulkovoDispatcher

    fun removeReducer(reducer: IReducer)

}