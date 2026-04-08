package com.papidoc.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Vaccines
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Modelo de cada feature/card visible en el Home.
 * Para agregar una nueva feature basta con agregarla a la lista [features].
 */
data class HomeFeature(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val enabled: Boolean,
    val route: String? = null
)

/**
 * Lista configurable de features. Las cards "Próximamente" se muestran
 * deshabilitadas. Para habilitar una, basta cambiar [enabled] a true
 * y asignar una [route].
 */
private val features = listOf(
    HomeFeature(
        title = "Calculadora de Paracetamol",
        description = "Calcula la dosis en gotas y mL según el peso de tu bebé",
        icon = Icons.Default.Calculate,
        enabled = true,
        route = "dosage_calculator"
    ),
    HomeFeature(
        title = "Registro de Temperatura",
        description = "Lleva un control de la temperatura de tu bebé",
        icon = Icons.Default.Thermostat,
        enabled = false
    ),
    HomeFeature(
        title = "Control de Vacunas",
        description = "Registra y programa las vacunas de tu bebé",
        icon = Icons.Default.Vaccines,
        enabled = false
    ),
    HomeFeature(
        title = "Otros Medicamentos",
        description = "Calculadora para ibuprofeno y otros medicamentos",
        icon = Icons.Default.MedicalServices,
        enabled = false
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDosageCalculator: () -> Unit,
    onNavigateToDisclaimer: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PapiDoc",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToDisclaimer) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Ver aviso legal",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Herramientas para papás y mamás",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(features) { feature ->
                FeatureCard(
                    feature = feature,
                    onClick = {
                        if (feature.enabled) {
                            when (feature.route) {
                                "dosage_calculator" -> onNavigateToDosageCalculator()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(feature: HomeFeature, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        enabled = feature.enabled,
        colors = CardDefaults.cardColors(
            containerColor = if (feature.enabled) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = if (feature.enabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (feature.enabled) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        }
                    )
                    if (!feature.enabled) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Próximamente",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (feature.enabled) {
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    }
                )
            }
        }
    }
}
