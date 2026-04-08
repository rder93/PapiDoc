package com.papidoc.domain.model

/**
 * Resultado del cálculo de dosis de un medicamento.
 * Genérico: no está atado a paracetamol, puede usarse para otros
 * medicamentos en el futuro cambiando los factores de cálculo.
 */
data class DosageResult(
    val minDoseMg: Double,
    val maxDoseMg: Double,
    val minDoseMl: Double,
    val maxDoseMl: Double,
    val minDoseDrops: Int,
    val maxDoseDrops: Int,
    val maxDailyDoseMg: Double,
    val maxDailyDoseDrops: Int,
    val intervalHoursMin: Int = 4,
    val intervalHoursMax: Int = 6,
    val maxDailyDoses: Int = 4,
    val concentrationMgPerMl: Int,
    val weightKg: Double
)
