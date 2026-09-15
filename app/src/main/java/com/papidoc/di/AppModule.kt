package com.papidoc.di

import androidx.room.Room
import com.papidoc.data.local.PapiDocDatabase
import com.papidoc.data.repository.DisclaimerRepositoryImpl
import com.papidoc.data.repository.DoseHistoryRepositoryImpl
import com.papidoc.domain.repository.DisclaimerRepository
import com.papidoc.domain.repository.DoseHistoryRepository
import com.papidoc.domain.usecase.CalculateDosageUseCase
import com.papidoc.presentation.disclaimer.DisclaimerViewModel
import com.papidoc.presentation.dosage.DosageCalculatorViewModel
import com.papidoc.presentation.history.DoseHistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single {
        Room.databaseBuilder(androidContext(), PapiDocDatabase::class.java, "papidoc_db")
            .build()
    }
    single { get<PapiDocDatabase>().doseHistoryDao() }

    // Repositories
    single<DisclaimerRepository> { DisclaimerRepositoryImpl(androidContext()) }
    single<DoseHistoryRepository> { DoseHistoryRepositoryImpl(get()) }

    // Use Cases
    factory { CalculateDosageUseCase() }

    // ViewModels
    viewModel { DisclaimerViewModel(get()) }
    viewModel { DosageCalculatorViewModel(get(), get()) }
    viewModel { DoseHistoryViewModel(get()) }
}
