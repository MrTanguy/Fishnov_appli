package com.example.fishnov.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepository(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bearer_token")

    private object Keys {
        val BEARER_TOKEN = stringPreferencesKey("bearer_token")
    }

    private val dataStore = context.dataStore

    suspend fun saveBearerToken(bearerToken: String) {
        dataStore.edit { preferences ->
            preferences[Keys.BEARER_TOKEN] = bearerToken
        }
    }

    val bearerTokenFlow: Flow<String>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[Keys.BEARER_TOKEN] ?: ""
            }

}
