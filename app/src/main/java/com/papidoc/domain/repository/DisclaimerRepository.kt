package com.papidoc.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Contrato para persistir si el usuario ya aceptó el disclaimer médico.
 */
interface DisclaimerRepository {
    val isDisclaimerAccepted: Flow<Boolean>
    suspend fun acceptDisclaimer()
}
