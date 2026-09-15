package com.papidoc.data.repository

import com.papidoc.data.local.DoseHistoryDao
import com.papidoc.data.local.DoseHistoryEntity
import com.papidoc.domain.model.DoseHistoryEntry
import com.papidoc.domain.repository.DoseHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DoseHistoryRepositoryImpl(
    private val dao: DoseHistoryDao
) : DoseHistoryRepository {

    override suspend fun logDose(entry: DoseHistoryEntry) {
        dao.insert(entry.toEntity())
    }

    override fun observeAll(): Flow<List<DoseHistoryEntry>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun delete(id: Long) = dao.deleteById(id)
}

fun DoseHistoryEntry.toEntity() = DoseHistoryEntity(
    id = id,
    concentrationMgPerMl = concentrationMgPerMl,
    weightKg = weightKg,
    doseMg = doseMg,
    doseMl = doseMl,
    doseDrops = doseDrops,
    timestamp = timestamp
)

fun DoseHistoryEntity.toDomain() = DoseHistoryEntry(
    id = id,
    concentrationMgPerMl = concentrationMgPerMl,
    weightKg = weightKg,
    doseMg = doseMg,
    doseMl = doseMl,
    doseDrops = doseDrops,
    timestamp = timestamp
)
