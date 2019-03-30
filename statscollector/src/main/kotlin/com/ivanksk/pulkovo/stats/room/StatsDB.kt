package com.ivanksk.pulkovo.stats.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [MeasureRecordEntry::class], version = 2, exportSchema = false)
abstract class StatsDB : RoomDatabase() {

    abstract fun measureDao(): MeasureRecordDao

    companion object {

        fun statsDb(context: Context) =
            Room.databaseBuilder(context.applicationContext, StatsDB::class.java, "statsDB")
                //.allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }


}