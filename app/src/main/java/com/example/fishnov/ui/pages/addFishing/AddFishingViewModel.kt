package com.example.fishnov.ui.pages.addFishing

import android.app.Application
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.classes.DataStoreObject
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class AddFishingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = API()
    private val context = getApplication<Application>()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)
    fun callToken(): String = runBlocking {
        return@runBlocking dataStoreRepository.getBearerToken()
    }

    fun callId(): Int = runBlocking {
        return@runBlocking dataStoreRepository.getUserId()
    }

    fun callAPIaddFishing(jsonObject: JSONObject): Result<DataStoreObject> = runBlocking {
        return@runBlocking try {

            val response = repository.addFishing(callToken(), jsonObject, callId())

            val answer = JSONObject(response)
            val accessToken = answer.optString("access_token")
            val id = answer.optInt("id")

            if (accessToken.isNotEmpty() and (id.toString() != "0")) {
                val dataStoreObject = DataStoreObject(accessToken, id)
                // Connexion réussie, DataStoreObject retourné
                Result.success(dataStoreObject)
            } else {
                // Erreur de connexion, retournez une erreur avec le message approprié
                val errorMessage = answer.optString("message", "Login failed")
                Result.failure(Exception(errorMessage))
            }

        } catch (e: Exception) {
            // Autre erreur, retournez l'exception
            Result.failure(e)
        }
    }

    fun saveToDataStoreRepository(bearerToken: String) {
        viewModelScope.launch {
            dataStoreRepository.saveBearerToken(bearerToken)
        }
    }
}