package com.ivanksk.pulkovo.stats

import android.content.Context
import com.ivanksk.pulkovo.operations.IMeasureRecord
import com.ivanksk.pulkovo.operations.models.MeasureRecord
import com.ivanksk.pulkovo.stats.models.Average
import com.ivanksk.pulkovo.stats.models.Median
import com.ivanksk.pulkovo.stats.room.MeasureRecordEntry
import com.ivanksk.pulkovo.stats.room.StatsDB
import java.util.concurrent.*
import kotlin.math.max
import kotlin.math.min


class StatsReducer(context: Context) : IStatsReducer {

    private val db = StatsDB.statsDb(context)

    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    private val decodeWorkQueue: BlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()
    // Sets the amount of time an idle thread waits before terminating
    private val KEEP_ALIVE_TIME = 1L
    // Sets the Time Unit to seconds
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    // Creates a thread pool manager
    private val tpe: ThreadPoolExecutor = ThreadPoolExecutor(
        NUMBER_OF_CORES,       // Initial pool size
        NUMBER_OF_CORES,       // Max pool size
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        decodeWorkQueue
    )

    override fun allRecords(): List<IMeasureRecord> =
        db.measureDao().all()
            .map {
                MeasureRecord(it.label, it.duration, it.timeStamp, it.optionalPayload)
            }

    override fun allMedians(): List<Median> =
        db.measureDao().eventsNames()
            .map {
                val values = db.measureDao()
                    .label(it)
                    .map { item ->
                        item.duration
                    }

                Median(it, values.median(), values.max() ?: 0, values.min() ?: 0)
            }


    // todo
    override fun allAverage(): List<Average> =
        db.measureDao().eventsNames()
            .map {
                val values = db.measureDao()
                    .label(it)
                    .map { item ->
                        item.duration
                    }

                Average(it, values.average(), values.max() ?: 0, values.min() ?: 0)
            }

    override fun store(record: IMeasureRecord) {
        tpe.execute {
            db.measureDao().add(
                MeasureRecordEntry(
                    0,
                    record.label(),
                    record.duration(),
                    record.timeStamp(),
                    record.optionalPayload() ?: ""
                )
            )
        }

    }

    override fun clear() {
        db.clearAllTables()
    }


    fun List<Long>.median() = this.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }
}