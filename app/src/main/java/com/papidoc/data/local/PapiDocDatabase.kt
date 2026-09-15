package com.papidoc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DoseHistoryEntity::class], version = 1, exportSchema = false)
abstract class PapiDocDatabase : RoomDatabase() {
    abstract fun doseHistoryDao(): DoseHistoryDao
}
