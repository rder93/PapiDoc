package com.papidoc.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papidoc.domain.model.DoseHistoryEntry
import com.papidoc.domain.repository.DoseHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DoseHistoryUiState(
    val entries: List<DoseHistoryEntry> = emptyList(),
    val isLoading: Boolean = true
)

class DoseHistoryViewModel(
    private val repository: DoseHistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoseHistoryUiState())
    val uiState: StateFlow<DoseHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeAll().collect { list ->
                _uiState.update { it.copy(entries = list, isLoading = false) }
            }
        }
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}
