package com.papidoc.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.papidoc.presentation.disclaimer.DisclaimerScreen
import com.papidoc.presentation.disclaimer.DisclaimerViewModel
import com.papidoc.presentation.dosage.DosageCalculatorScreen
import com.papidoc.presentation.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PapiDocNavGraph(navController: NavHostController) {
    val disclaimerViewModel: DisclaimerViewModel = koinViewModel()
    val disclaimerAccepted by disclaimerViewModel.disclaimerAccepted.collectAsState()

    val startDestination = if (disclaimerAccepted) Routes.HOME else Routes.DISCLAIMER

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.DISCLAIMER) {
            DisclaimerScreen(
                onAccepted = {
                    disclaimerViewModel.acceptDisclaimer()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.DISCLAIMER) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToDosageCalculator = {
                    navController.navigate(Routes.DOSAGE_CALCULATOR)
                },
                onNavigateToDisclaimer = {
                    navController.navigate(Routes.DISCLAIMER_READ_ONLY)
                }
            )
        }

        composable(Routes.DISCLAIMER_READ_ONLY) {
            DisclaimerScreen(
                isReadOnly = true,
                onAccepted = {},
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.DOSAGE_CALCULATOR) {
            DosageCalculatorScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
