package com.papidoc.presentation.dosage

import androidx.lifecycle.ViewModel
import com.papidoc.domain.model.DosageResult
import com.papidoc.domain.model.DosageValidation
import com.papidoc.domain.model.MedicationConcentration
import com.papidoc.domain.usecase.CalculateDosageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DosageUiState(
    val weightInput: String = "",
    val selectedConcentration: MedicationConcentration = MedicationConcentration.HIGH,
    val result: DosageResult? = null,
    val validationError: String? = null,
    val showWarning: Boolean = false,
    val warningMessage: String? = null
)

class DosageCalculatorViewModel(
    private val calculateDosageUseCase: CalculateDosageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DosageUiState())
    val uiState: StateFlow<DosageUiState> = _uiState.asStateFlow()

    fun onWeightChanged(weight: String) {
        // Permitir solo dígitos y un punto decimal
        val filtered = weight.filter { it.isDigit() || it == '.' }
        if (filtered.count { it == '.' } <= 1) {
            _uiState.update {
                it.copy(weightInput = filtered, result = null, validationError = null)
            }
        }
    }

    fun onConcentrationChanged(concentration: MedicationConcentration) {
        _uiState.update { it.copy(selectedConcentration = concentration, result = null) }
    }

    fun calculate() {
        val weight = _uiState.value.weightInput.toDoubleOrNull()
        if (weight == null) {
            _uiState.update {
                it.copy(validationError = "Ingresa un peso válido", result = null)
            }
            return
        }

        when (val validation = calculateDosageUseCase.validate(weight)) {
            is DosageValidation.Valid -> {
                val result = calculateDosageUseCase.calculate(
                    weight,
                    _uiState.value.selectedConcentration
                )
                _uiState.update {
                    it.copy(
                        result = result,
                        validationError = null,
                        showWarning = false,
                        warningMessage = null
                    )
                }
            }
            is DosageValidation.WeightOutOfRange -> {
                _uiState.update {
                    it.copy(
                        result = null,
                        validationError = null,
                        showWarning = true,
                        warningMessage = validation.message
                    )
                }
            }
            is DosageValidation.InvalidWeight -> {
                _uiState.update {
                    it.copy(
                        result = null,
                        validationError = validation.message,
                        showWarning = false,
                        warningMessage = null
                    )
                }
            }
        }
    }

    fun clearResult() {
        _uiState.update { DosageUiState() }
    }
}
