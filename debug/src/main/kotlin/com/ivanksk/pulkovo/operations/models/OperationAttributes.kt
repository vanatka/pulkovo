package com.ivanksk.pulkovo.operations.models

data class OperationAttributes(
    val name: String?,
    val klass: String?,
    val methodName: String?
) {

    fun label() = name ?: "$klass::$methodName"

}
