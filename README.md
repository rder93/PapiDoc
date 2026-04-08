# PapiDoc

Aplicación Android orientada a **padres y madres con bebés** que necesitan calcular dosis pediátricas de forma rápida y confiable. Nace como una calculadora de paracetamol en gotas, pero está diseñada para escalar hacia una plataforma integral de apoyo parental.

> **Aviso:** Esta app es una herramienta orientativa. No reemplaza la consulta médica ni el consejo de un profesional de la salud.

---

## Funcionalidades (V1)

| Funcionalidad | Estado |
|---|---|
| Calculadora de Paracetamol (gotas y mL) | Disponible |
| Disclaimer médico obligatorio | Disponible |
| Registro de Temperatura | Próximamente |
| Control de Vacunas | Próximamente |
| Otros Medicamentos (ibuprofeno, etc.) | Próximamente |

### Calculadora de Paracetamol

- Ingreso de peso del bebé (2 – 40 kg)
- Selector de concentración del frasco: **100 mg/mL** o **50 mg/mL**
- Resultado detallado:
  - Dosis mínima y máxima en **gotas**, **mL** y **mg**
  - Intervalo recomendado (cada 4 a 6 horas)
  - Máximo diario en mg y gotas
- Validación de peso con advertencias fuera de rango
- Recordatorio permanente de consultar al médico

### Lógica de cálculo

| Parámetro | Valor |
|---|---|
| Dosis mínima | 10 mg/kg |
| Dosis máxima | 15 mg/kg |
| Dosis máxima diaria | 60 mg/kg |
| Gotas por mL | 30 (estándar Chile) |
| Máximo dosis por día | 4 |

---

## Stack tecnológico

| Componente | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose (Material Design 3) |
| Arquitectura | MVVM + Clean Architecture |
| Inyección de dependencias | Koin |
| Navegación | Navigation Compose |
| Persistencia | DataStore Preferences |
| Testing | JUnit 4 + MockK |

---

## Arquitectura del proyecto

```
com.papidoc/
├── core/
│   ├── navigation/          # NavGraph, rutas y destinos
│   └── ui/theme/            # Color, Typography, Theme (M3 light + dark)
├── data/
│   └── repository/          # Implementaciones (DataStore)
├── di/                      # Módulo Koin
├── domain/
│   ├── model/               # DosageResult, MedicationConcentration, DosageValidation
│   ├── repository/          # Interfaces
│   └── usecase/             # CalculateDosageUseCase
└── presentation/
    ├── components/          # Composables reutilizables (PapiDocButton)
    ├── disclaimer/          # Pantalla de aviso legal
    ├── dosage/              # Calculadora de dosis
    └── home/                # Pantalla principal con cards de features
```

---

## Requisitos

- Android Studio Hedgehog o superior
- JDK 17
- Android SDK 35
- Dispositivo o emulador con Android 8.0+ (API 26)

## Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/rder93/PapiDoc.git
   ```
2. Abre el proyecto en Android Studio.
3. Espera a que termine el Gradle sync.
4. Ejecuta la app en un emulador o dispositivo físico.

## Tests

Ejecuta los tests unitarios del caso de uso de dosificación:

```bash
./gradlew test
```

Los tests cubren:
- Validación de peso (rango válido, fuera de rango, peso inválido)
- Cálculos con concentración 100 mg/mL y 50 mg/mL
- Casos borde (peso mínimo 2 kg, peso máximo 40 kg)
- Verificación de gotas, mL, mg y máximo diario

---

## Diseño

- Paleta de colores suave: celeste, verde menta y durazno
- Tipografía grande y legible (pensada para uso nocturno bajo estrés)
- Soporte para **modo claro y oscuro**
- Botones de 56dp para facilitar el toque
- App 100% offline, sin backend ni permisos de red

---

## Configuración del proyecto

| Propiedad | Valor |
|---|---|
| Package name | `com.papidoc` |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 35 |
| Compile SDK | 35 |
| JVM Target | 17 |

---

## Licencia

Este proyecto es de uso privado. Todos los derechos reservados.
