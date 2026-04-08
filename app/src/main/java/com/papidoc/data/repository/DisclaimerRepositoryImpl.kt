package com.papidoc.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.papidoc.domain.repository.DisclaimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "papidoc_prefs")

class DisclaimerRepositoryImpl(private val context: Context) : DisclaimerRepository {

    companion object {
        private val DISCLAIMER_ACCEPTED = booleanPreferencesKey("disclaimer_accepted")
    }

    override val isDisclaimerAccepted: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[DISCLAIMER_ACCEPTED] ?: false
        }

    override suspend fun acceptDisclaimer() {
        context.dataStore.edit { preferences ->
            preferences[DISCLAIMER_ACCEPTED] = true
        }
    }
}
