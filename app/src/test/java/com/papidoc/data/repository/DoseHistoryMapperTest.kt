package com.papidoc.data.repository

import com.papidoc.domain.model.DoseHistoryEntry
import org.junit.Assert.assertEquals
import org.junit.Test

class DoseHistoryMapperTest {

    @Test
    fun `entry to entity and back preserves all fields`() {
        val entry = DoseHistoryEntry(
            id = 42L,
            concentrationMgPerMl = 100,
            weightKg = 7.5,
            doseMg = 112.5,
            doseMl = 1.125,
            doseDrops = 34,
            timestamp = 1_700_000_000_000L
        )

        val roundTripped = entry.toEntity().toDomain()

        assertEquals(entry, roundTripped)
    }
}
