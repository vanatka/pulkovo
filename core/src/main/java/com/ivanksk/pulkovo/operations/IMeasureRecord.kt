package com.ivanksk.pulkovo.operations

/**
 * created by vanatka MAR 25 2019
 */

interface IMeasureRecord {

    fun label(): String

    fun className(): String? = null

    fun methodName(): String? = null

    fun duration(): Long

    fun timeStamp(): Long

    fun optionalPayload(): String?
}