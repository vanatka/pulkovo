package com.ivanksk.pulkovo.stats.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "measureRecords")
class MeasureRecordEntry(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")var id: Int,
    @ColumnInfo(name = "label") var label: String = "",
    @ColumnInfo(name = "duration") var duration: Long,
    @ColumnInfo(name = "timeStamp") var timeStamp: Long,
    @ColumnInfo(name = "optionalPayload") var optionalPayload: String = ""
)