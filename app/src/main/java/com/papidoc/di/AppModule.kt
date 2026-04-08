package com.papidoc.di

import com.papidoc.data.repository.DisclaimerRepositoryImpl
import com.papidoc.domain.repository.DisclaimerRepository
import com.papidoc.domain.usecase.CalculateDosageUseCase
import com.papidoc.presentation.disclaimer.DisclaimerViewModel
import com.papidoc.presentation.dosage.DosageCalculatorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<DisclaimerRepository> { DisclaimerRepositoryImpl(androidContext()) }

    // Use Cases
    factory { CalculateDosageUseCase() }

    // ViewModels
    viewModel { DisclaimerViewModel(get()) }
    viewModel { DosageCalculatorViewModel(get()) }
}
