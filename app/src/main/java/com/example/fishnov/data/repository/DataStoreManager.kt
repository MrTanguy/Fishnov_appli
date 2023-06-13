package com.example.fishnov.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager (context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name="BEARER_TOKEN")
    private val dataStore = context.dataStore

    companion object {
        val bearerTokenKey = stringPreferencesKey("BEARER_TOKEN")
    }

    suspend fun  setBearer(bearerToken : String) {
        dataStore.edit { pref->
            pref[bearerTokenKey] = bearerToken
        }
    }

    fun getBearer(key: Preferences.Key<String>, defaultValue: String): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

}

