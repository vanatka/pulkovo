package com.ivanksk.pulkovo.operations

inline fun <R> measureMethod(objectReference: Any, block: () -> R): R = block()

inline fun <R> measureMethodWithName(operationName: String, block: () -> R): R = block()

public inline fun <R> measureMethod(objectReference: Any, payload: String? = null, block: () -> R): R =
    block()

/**
 * That method is used to measure method's execution time
 * @param input - reference to the object instance, just "this",
 * class label and method label will be extracted automatically
 * @param block - code block to be measured
 */
public inline fun <R> measureMethodWithLabel(operationName: String, payload: String? = null, block: () -> R): R =
    block()


inline fun <R> startLabelMeasure(
    label: String,
    payload: String? = null,
    block: () -> R
): R = block()

inline fun <R> stopLabelMeasure(
    label: String,
    block: () -> R
): R = block()

inline fun startLabelMeasure(label: String, payload: String? = null) {}

inline fun stopLabelMeasure(label: String) {}