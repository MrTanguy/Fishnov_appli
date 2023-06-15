package com.example.fishnov.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreRepository private constructor(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bearerToken")

    private object Keys {
        val BEARER_TOKEN = stringPreferencesKey("bearerToken")
        val USER_ID = intPreferencesKey("userId")
    }

    private val dataStore = context.dataStore

    suspend fun saveBearerToken(bearerToken: String) {
        dataStore.edit { preferences ->
            preferences[Keys.BEARER_TOKEN] = bearerToken
        }
    }

    suspend fun getBearerToken(): String {
        return dataStore.data.first()[Keys.BEARER_TOKEN] ?: ""
    }

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_ID] = userId
        }
    }

    suspend fun getUserId(): Int {
        return dataStore.data.first()[Keys.USER_ID] ?: 0
    }

    companion object {
        private var instance: DataStoreRepository? = null

        fun getInstance(context: Context): DataStoreRepository {
            return instance ?: synchronized(this) {
                instance ?: DataStoreRepository(context.applicationContext).also { instance = it }
            }
        }
    }
}
