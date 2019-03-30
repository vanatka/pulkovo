package com.ivanksk.pulkovo.operations

import com.ivanksk.pulkovo.dispatcher.PulkovoDispatcher
import com.ivanksk.pulkovo.operations.models.MeasureRecord
import com.ivanksk.pulkovo.operations.models.OperationAttributes

/**
 * created by vanatka MAR 25 2019
 */

// probably it'll be used later to be able +,-,%,/ and etc
// to perform fun operations :)
//interface MeasureOperator {
//
//    operator fun <R> invoke(input: Any, block: () -> R): R
//
//}

/**
 * That method is used to measure method's execution time
 * @param objectReference - reference to the object instance, just "this",
 * class label and method label will be extracted automatically
 * @param block - code block to be measured
 */
public inline fun <R> measureMethod(objectReference: Any, payload: String? = null, block: () -> R): R =
    measureOperationImpl(extractMethodAndClassName(objectReference), payload, block)

/**
 * That method is used to measure method's execution time
 * @param input - reference to the object instance, just "this",
 * class label and method label will be extracted automatically
 * @param block - code block to be measured
 */
public inline fun <R> measureMethodWithLabel(operationName: String, payload: String? = null, block: () -> R): R =
    measureOperationImpl(OperationAttributes(operationName, null, null), payload, block)

inline fun <R> measureOperationImpl(operation: OperationAttributes, payload: String?, block: () -> R): R {
    val start = System.currentTimeMillis()

    return try {
        block()
    } finally {
        val end = System.currentTimeMillis()

        PulkovoDispatcher.processMeasurement(
            MeasureRecord(operation.label(), end - start, end, payload)
        )
    }
}



/**********************************************  **********************************************/

val complexOperationsRegistry = HashMap<String, IMeasureRecord>()

inline fun <R> startLabelMeasure(
    label: String,
    payload: String? = null,
    block: () -> R
): R {
    val now = System.currentTimeMillis()
    val previousEntry = complexOperationsRegistry.put(label, MeasureRecord(label, 0, now, payload))
    processEntryIfNotNull(previousEntry, now)

    return block()
}

inline fun <R> stopLabelMeasure(
    label: String,
    block: () -> R
): R {
    val previousEntry = complexOperationsRegistry[label]

    return try {
        block()
    } finally {
        processEntryIfNotNull(previousEntry, System.currentTimeMillis())
    }
}

inline fun startLabelMeasure(label: String, payload: String? = null) {
    val now = System.currentTimeMillis()
    val previousEntry = complexOperationsRegistry.put(label, MeasureRecord(label, 0, now, payload))
    processEntryIfNotNull(previousEntry, now)
}

inline fun stopLabelMeasure(label: String) {
    val previousEntry = complexOperationsRegistry.remove(label)

    processEntryIfNotNull(previousEntry, System.currentTimeMillis())
}

fun processEntryIfNotNull(record: IMeasureRecord?, end: Long) {
    if (record != null) {
        PulkovoDispatcher.processMeasurement(
            MeasureRecord(record.label(), end - record.timeStamp(), record.timeStamp(), record.optionalPayload())
        )
    }
}


/*********************************************** Stack manipulations ***********************************************/

// yes it's hardcoded and depended on methods structure
// however it works :) So "if it works - don't touch" :)
// todo probably re-think implementation
private fun getMethodName() = Thread.currentThread().stackTrace[4].methodName

fun extractMethodAndClassName(klass: Any) =
    OperationAttributes(null, klass.javaClass.simpleName, getMethodName())
