package com.papidoc.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dose_history")
data class DoseHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val concentrationMgPerMl: Int,
    val weightKg: Double,
    val doseMg: Double,
    val doseMl: Double,
    val doseDrops: Int,
    val timestamp: Long
)
