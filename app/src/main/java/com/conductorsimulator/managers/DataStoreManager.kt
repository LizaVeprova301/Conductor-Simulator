package com.conductorsimulator.managers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data")

class DataStoreManager(private val context: Context) {
    suspend fun saveHighScore(score: Int) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("highScore")] = score
        }
    }

    fun getHighScore() = context.dataStore.data.map { pref ->
        return@map pref[intPreferencesKey("highScore")] ?: 0
    }

}