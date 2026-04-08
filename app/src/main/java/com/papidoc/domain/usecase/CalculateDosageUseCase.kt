package com.papidoc.domain.usecase

import com.papidoc.domain.model.DosageResult
import com.papidoc.domain.model.DosageValidation
import com.papidoc.domain.model.MedicationConcentration
import kotlin.math.roundToInt

/**
 * Caso de uso para calcular la dosis de un medicamento en gotas pediátricas.
 *
 * Fórmula base (paracetamol):
 * - Dosis mínima: peso_kg × 10 mg/kg
 * - Dosis máxima: peso_kg × 15 mg/kg
 * - Dosis máxima diaria: peso_kg × 60 mg/kg (no superar 4 dosis en 24h)
 * - 1 mL = 20 gotas (estándar gotero pediátrico)
 *
 * Diseñado para ser reutilizable con otros medicamentos cambiando los factores.
 */
class CalculateDosageUseCase {

    companion object {
        const val MIN_WEIGHT_KG = 2.0
        const val MAX_WEIGHT_KG = 40.0
        const val MIN_DOSE_MG_PER_KG = 10.0
        const val MAX_DOSE_MG_PER_KG = 15.0
        const val MAX_DAILY_MG_PER_KG = 60.0
        const val DROPS_PER_ML = 30
    }

    /**
     * Valida que el peso ingresado esté dentro del rango aceptable.
     */
    fun validate(weightKg: Double): DosageValidation {
        if (weightKg <= 0) {
            return DosageValidation.InvalidWeight("El peso debe ser mayor a 0 kg")
        }
        if (weightKg < MIN_WEIGHT_KG || weightKg > MAX_WEIGHT_KG) {
            return DosageValidation.WeightOutOfRange(
                "El peso debe estar entre $MIN_WEIGHT_KG y $MAX_WEIGHT_KG kg. " +
                    "Consulta a tu médico para dosis fuera de este rango."
            )
        }
        return DosageValidation.Valid
    }

    /**
     * Calcula la dosis mínima, máxima, en gotas, mL y mg.
     * Debe llamarse solo si [validate] retorna [DosageValidation.Valid].
     */
    fun calculate(weightKg: Double, concentration: MedicationConcentration): DosageResult {
        val minDoseMg = weightKg * MIN_DOSE_MG_PER_KG
        val maxDoseMg = weightKg * MAX_DOSE_MG_PER_KG
        val maxDailyDoseMg = weightKg * MAX_DAILY_MG_PER_KG

        val minDoseMl = minDoseMg / concentration.mgPerMl
        val maxDoseMl = maxDoseMg / concentration.mgPerMl

        val minDoseDrops = (minDoseMl * DROPS_PER_ML).roundToInt()
        val maxDoseDrops = (maxDoseMl * DROPS_PER_ML).roundToInt()

        val maxDailyDoseMl = maxDailyDoseMg / concentration.mgPerMl
        val maxDailyDoseDrops = (maxDailyDoseMl * DROPS_PER_ML).roundToInt()

        return DosageResult(
            minDoseMg = minDoseMg,
            maxDoseMg = maxDoseMg,
            minDoseMl = minDoseMl,
            maxDoseMl = maxDoseMl,
            minDoseDrops = minDoseDrops,
            maxDoseDrops = maxDoseDrops,
            maxDailyDoseMg = maxDailyDoseMg,
            maxDailyDoseDrops = maxDailyDoseDrops,
            concentrationMgPerMl = concentration.mgPerMl,
            weightKg = weightKg
        )
    }
}
