package com.papidoc.presentation.disclaimer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.papidoc.presentation.components.PapiDocButton

/**
 * Pantalla de disclaimer médico.
 * @param isReadOnly true cuando el usuario accede desde Home para releer.
 *                   En ese caso muestra TopAppBar con flecha atrás y botón "Volver".
 * @param onAccepted callback al aceptar por primera vez (navega a Home).
 * @param onNavigateBack callback para volver al Home (solo en modo lectura).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisclaimerScreen(
    onAccepted: () -> Unit,
    isReadOnly: Boolean = false,
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            if (isReadOnly) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Aviso legal",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.HealthAndSafety,
                contentDescription = null,
                modifier = Modifier.height(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PapiDoc",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu asistente de dosificación pediátrica",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Aviso importante",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Esta aplicación es una herramienta orientativa para el " +
                            "cálculo de dosis pediátricas. NO reemplaza la consulta médica " +
                            "ni el consejo de un profesional de la salud.\n\n" +
                            "Siempre consulta a tu médico o farmacéutico antes de " +
                            "administrar cualquier medicamento a tu hijo/a.\n\n" +
                            "Los resultados son aproximados y se basan en rangos " +
                            "terapéuticos estándar. Cada niño/a es diferente y puede " +
                            "requerir ajustes individuales.\n\n" +
                            "Los desarrolladores de PapiDoc no se hacen responsables " +
                            "del uso indebido de la información proporcionada.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (isReadOnly) {
                PapiDocButton(
                    text = "Volver",
                    onClick = onNavigateBack
                )
            } else {
                PapiDocButton(
                    text = "Entendido, continuar",
                    onClick = onAccepted
                )
            }
        }
    }
}
