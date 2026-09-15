package com.papidoc.domain.repository

import com.papidoc.domain.model.DoseHistoryEntry
import kotlinx.coroutines.flow.Flow

interface DoseHistoryRepository {
    suspend fun logDose(entry: DoseHistoryEntry)
    fun observeAll(): Flow<List<DoseHistoryEntry>>
    suspend fun delete(id: Long)
}
