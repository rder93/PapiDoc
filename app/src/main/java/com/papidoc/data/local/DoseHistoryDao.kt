package com.papidoc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DoseHistoryDao {

    @Insert
    suspend fun insert(entity: DoseHistoryEntity)

    @Query("SELECT * FROM dose_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<DoseHistoryEntity>>

    @Query("DELETE FROM dose_history WHERE id = :id")
    suspend fun deleteById(id: Long)
}
