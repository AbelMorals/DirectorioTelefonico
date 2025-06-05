package com.example.onboardingapp.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnboardingStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("onboarding_prefs")
        val STORE_BOARD = booleanPreferencesKey("onboarding_completed")
        val Dark_Mode = booleanPreferencesKey("dark_mode")
    }

    val isDarkModeOn: Flow<Boolean> = context.dataStore.data
    .map { prefs -> prefs[Dark_Mode] ?: false }

    suspend fun setDarkMode(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Dark_Mode] = value }
    }

    val getStoreBoarding: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[STORE_BOARD] ?: false
        }

    suspend fun saveBoarding(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[STORE_BOARD] = value
        }
    }
}