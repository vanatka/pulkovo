package com.ivanksk.pulkovo.operations

inline fun <R> measureMethod(objectReference: Any, block: () -> R): R = block()

inline fun <R> measureMethodWithName(operationName: String, block: () -> R): R = block()