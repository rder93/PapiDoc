package com.papidoc.domain.model

/**
 * Resultado de la validación del peso ingresado por el usuario.
 */
sealed class DosageValidation {
    data object Valid : DosageValidation()
    data class WeightOutOfRange(val message: String) : DosageValidation()
    data class InvalidWeight(val message: String) : DosageValidation()
}
