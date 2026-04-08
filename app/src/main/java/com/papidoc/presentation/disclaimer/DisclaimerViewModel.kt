package com.papidoc.presentation.disclaimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papidoc.domain.repository.DisclaimerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DisclaimerViewModel(
    private val disclaimerRepository: DisclaimerRepository
) : ViewModel() {

    val disclaimerAccepted = disclaimerRepository.isDisclaimerAccepted
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun acceptDisclaimer() {
        viewModelScope.launch {
            disclaimerRepository.acceptDisclaimer()
        }
    }
}
