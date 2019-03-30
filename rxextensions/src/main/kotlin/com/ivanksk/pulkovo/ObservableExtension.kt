package com.ivanksk.pulkovo

import com.ivanksk.pulkovo.operations.startLabelMeasure
import com.ivanksk.pulkovo.operations.stopLabelMeasure
import io.reactivex.Observable


inline fun <T> startMeasure(label: String, observable: Observable<T>): Observable<T> {
    startLabelMeasure(label)

    return observable
}

fun <T> Observable<T>.startMeasure(label: String): Observable<T> = this.doOnNext {
    startLabelMeasure(label)
    android.util.Log.e(">>>", "starrrrr " + label)
    it
}

fun <T> Observable<T>.stopMeasure(label: String): Observable<T> = this.doOnComplete {
    stopLabelMeasure(label)
    android.util.Log.e(">>>", "stop " + label)
}
