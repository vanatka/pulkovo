package com.ivanksk.pulkovo.stats.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface MeasureRecordDao {

    @Query("select * from measureRecords")
    fun all(): List<MeasureRecordEntry>

    @Query("select distinct label from measureRecords")
    fun eventsNames(): List<String>

    @Query("select * from measureRecords where label like :label")
    fun label(label: String): List<MeasureRecordEntry>

    @Insert
    fun add(vararg measureRecord: MeasureRecordEntry)

}