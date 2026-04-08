package com.papidoc.domain.model

/**
 * Concentraciones disponibles de un medicamento en gotas.
 * Extensible para soportar otros medicamentos en futuras versiones.
 */
enum class MedicationConcentration(val mgPerMl: Int, val displayName: String) {
    HIGH(100, "100 mg/mL"),
    LOW(50, "50 mg/mL")
}
