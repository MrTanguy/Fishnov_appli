package com.example.fishnov.data.repository

import android.content.Context
import android.util.Log
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
        Log.d("DataStoreRepository", "saveBearerToken: $bearerToken")
        dataStore.edit { preferences ->
            preferences[Keys.BEARER_TOKEN] = bearerToken
        }
    }

    suspend fun getBearerToken(): String {
        val preferences = dataStore.data.first()
        val bearerToken = preferences[Keys.BEARER_TOKEN]
        return bearerToken ?: ""
    }

    suspend fun saveUserId(userId: Int) {
        Log.d("DataStoreRepository", "saveUserId: $userId")
        dataStore.edit { preferences ->
            preferences[Keys.USER_ID] = userId
        }
    }

    suspend fun getUserId(): Int {
        val preferences = dataStore.data.first()
        val userId = preferences[Keys.USER_ID]
        return userId ?: 0
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
