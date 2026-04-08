package com.papidoc.domain.usecase

import com.papidoc.domain.model.DosageValidation
import com.papidoc.domain.model.MedicationConcentration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CalculateDosageUseCaseTest {

    private lateinit var useCase: CalculateDosageUseCase

    @Before
    fun setup() {
        useCase = CalculateDosageUseCase()
    }

    // =========================================================================
    // Validation tests
    // =========================================================================

    @Test
    fun `validate returns Valid for weight within range`() {
        assertTrue(useCase.validate(5.0) is DosageValidation.Valid)
        assertTrue(useCase.validate(2.0) is DosageValidation.Valid)
        assertTrue(useCase.validate(40.0) is DosageValidation.Valid)
        assertTrue(useCase.validate(10.5) is DosageValidation.Valid)
    }

    @Test
    fun `validate returns InvalidWeight for zero or negative weight`() {
        assertTrue(useCase.validate(0.0) is DosageValidation.InvalidWeight)
        assertTrue(useCase.validate(-1.0) is DosageValidation.InvalidWeight)
        assertTrue(useCase.validate(-100.0) is DosageValidation.InvalidWeight)
    }

    @Test
    fun `validate returns WeightOutOfRange for weight below minimum`() {
        assertTrue(useCase.validate(1.9) is DosageValidation.WeightOutOfRange)
        assertTrue(useCase.validate(1.0) is DosageValidation.WeightOutOfRange)
        assertTrue(useCase.validate(0.5) is DosageValidation.WeightOutOfRange)
    }

    @Test
    fun `validate returns WeightOutOfRange for weight above maximum`() {
        assertTrue(useCase.validate(40.1) is DosageValidation.WeightOutOfRange)
        assertTrue(useCase.validate(50.0) is DosageValidation.WeightOutOfRange)
    }

    // =========================================================================
    // Calculation tests — 100 mg/mL (HIGH)
    // =========================================================================

    @Test
    fun `calculate with 10kg and 100mg concentration returns correct doses`() {
        val result = useCase.calculate(10.0, MedicationConcentration.HIGH)

        // Dosis en mg: 10*10=100, 10*15=150
        assertEquals(100.0, result.minDoseMg, 0.01)
        assertEquals(150.0, result.maxDoseMg, 0.01)
        // Dosis en mL: 100/100=1.0, 150/100=1.5
        assertEquals(1.0, result.minDoseMl, 0.01)
        assertEquals(1.5, result.maxDoseMl, 0.01)
        // Gotas: 1.0*30=30, 1.5*30=45
        assertEquals(30, result.minDoseDrops)
        assertEquals(45, result.maxDoseDrops)
        // Máx diario: 10*60=600 → 600/100=6.0mL → 6.0*30=180 gotas
        assertEquals(600.0, result.maxDailyDoseMg, 0.01)
        assertEquals(180, result.maxDailyDoseDrops)
    }

    @Test
    fun `calculate with 7_5kg and 100mg concentration returns correct doses`() {
        val result = useCase.calculate(7.5, MedicationConcentration.HIGH)

        assertEquals(75.0, result.minDoseMg, 0.01)
        assertEquals(112.5, result.maxDoseMg, 0.01)
        assertEquals(0.75, result.minDoseMl, 0.01)
        assertEquals(1.125, result.maxDoseMl, 0.01)
        assertEquals(23, result.minDoseDrops)           // 0.75*30 = 22.5 → round to 23
        assertEquals(34, result.maxDoseDrops)            // 1.125*30 = 33.75 → round to 34
        // Máx diario: 450/100=4.5mL → 4.5*30=135 gotas
        assertEquals(450.0, result.maxDailyDoseMg, 0.01)
        assertEquals(135, result.maxDailyDoseDrops)
    }

    // =========================================================================
    // Calculation tests — 50 mg/mL (LOW)
    // =========================================================================

    @Test
    fun `calculate with 10kg and 50mg concentration returns correct doses`() {
        val result = useCase.calculate(10.0, MedicationConcentration.LOW)

        assertEquals(100.0, result.minDoseMg, 0.01)
        assertEquals(150.0, result.maxDoseMg, 0.01)
        // mL duplicado con concentración baja: 100/50=2.0, 150/50=3.0
        assertEquals(2.0, result.minDoseMl, 0.01)
        assertEquals(3.0, result.maxDoseMl, 0.01)
        // Gotas: 2.0*30=60, 3.0*30=90
        assertEquals(60, result.minDoseDrops)
        assertEquals(90, result.maxDoseDrops)
        // Máx diario: 600/50=12.0mL → 12.0*30=360 gotas
        assertEquals(600.0, result.maxDailyDoseMg, 0.01)
        assertEquals(360, result.maxDailyDoseDrops)
    }

    @Test
    fun `calculate with 5kg and 50mg concentration returns correct doses`() {
        val result = useCase.calculate(5.0, MedicationConcentration.LOW)

        assertEquals(50.0, result.minDoseMg, 0.01)
        assertEquals(75.0, result.maxDoseMg, 0.01)
        assertEquals(1.0, result.minDoseMl, 0.01)
        assertEquals(1.5, result.maxDoseMl, 0.01)
        assertEquals(30, result.minDoseDrops)            // 1.0*30 = 30
        assertEquals(45, result.maxDoseDrops)            // 1.5*30 = 45
        // Máx diario: 300/50=6.0mL → 6.0*30=180 gotas
        assertEquals(300.0, result.maxDailyDoseMg, 0.01)
        assertEquals(180, result.maxDailyDoseDrops)
    }

    // =========================================================================
    // Edge cases
    // =========================================================================

    @Test
    fun `calculate with minimum weight 2kg returns correct doses`() {
        val result = useCase.calculate(2.0, MedicationConcentration.HIGH)

        assertEquals(20.0, result.minDoseMg, 0.01)
        assertEquals(30.0, result.maxDoseMg, 0.01)
        assertEquals(0.2, result.minDoseMl, 0.01)
        assertEquals(0.3, result.maxDoseMl, 0.01)
        assertEquals(6, result.minDoseDrops)             // 0.2*30 = 6
        assertEquals(9, result.maxDoseDrops)             // 0.3*30 = 9
        // Máx diario: 120/100=1.2mL → 1.2*30=36 gotas
        assertEquals(120.0, result.maxDailyDoseMg, 0.01)
        assertEquals(36, result.maxDailyDoseDrops)
    }

    @Test
    fun `calculate with maximum weight 40kg returns correct doses`() {
        val result = useCase.calculate(40.0, MedicationConcentration.HIGH)

        assertEquals(400.0, result.minDoseMg, 0.01)
        assertEquals(600.0, result.maxDoseMg, 0.01)
        assertEquals(4.0, result.minDoseMl, 0.01)
        assertEquals(6.0, result.maxDoseMl, 0.01)
        assertEquals(120, result.minDoseDrops)            // 4.0*30 = 120
        assertEquals(180, result.maxDoseDrops)            // 6.0*30 = 180
        // Máx diario: 2400/100=24.0mL → 24.0*30=720 gotas
        assertEquals(2400.0, result.maxDailyDoseMg, 0.01)
        assertEquals(720, result.maxDailyDoseDrops)
    }

    @Test
    fun `calculate preserves interval and max daily doses defaults`() {
        val result = useCase.calculate(10.0, MedicationConcentration.HIGH)

        assertEquals(4, result.intervalHoursMin)
        assertEquals(6, result.intervalHoursMax)
        assertEquals(4, result.maxDailyDoses)
    }

    @Test
    fun `calculate stores concentration and weight in result`() {
        val result = useCase.calculate(8.0, MedicationConcentration.LOW)

        assertEquals(50, result.concentrationMgPerMl)
        assertEquals(8.0, result.weightKg, 0.01)
    }

    @Test
    fun `calculate with HIGH concentration stores 100 in result`() {
        val result = useCase.calculate(8.0, MedicationConcentration.HIGH)

        assertEquals(100, result.concentrationMgPerMl)
    }
}
