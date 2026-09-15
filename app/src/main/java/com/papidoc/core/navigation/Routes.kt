package com.papidoc.core.navigation

/**
 * Destinos de navegación de la app.
 * Diseñado para ser fácilmente extensible: basta agregar una nueva
 * constante para habilitar una nueva ruta en el NavGraph.
 */
object Routes {
    const val DISCLAIMER = "disclaimer"
    const val HOME = "home"
    const val DOSAGE_CALCULATOR = "dosage_calculator"
    const val DISCLAIMER_READ_ONLY = "disclaimer_read_only"
    const val DOSE_HISTORY = "dose_history"
    // Futuras rutas:
    // const val TEMPERATURE_LOG = "temperature_log"
    // const val VACCINE_TRACKER = "vaccine_tracker"
}
