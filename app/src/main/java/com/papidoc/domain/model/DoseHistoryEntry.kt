package com.papidoc.domain.model

/**
 * Registro de una dosis administrada. Se guarda el valor máximo del rango
 * calculado (doseMg/doseMl/doseDrops) para no dejar ambigüedad sobre
 * cuál dosis exacta se le dio al bebé.
 */
data class DoseHistoryEntry(
    val id: Long = 0,
    val concentrationMgPerMl: Int,
    val weightKg: Double,
    val doseMg: Double,
    val doseMl: Double,
    val doseDrops: Int,
    val timestamp: Long = System.currentTimeMillis()
)
