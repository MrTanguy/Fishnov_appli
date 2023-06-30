package com.example.fishnov.ui.pages.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.classes.DataStoreObject
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = API()
    private val context = getApplication<Application>()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    fun callAPIlogin(loginForm: LoginForm): Result<DataStoreObject> = runBlocking {
        return@runBlocking try {

            val response = repository.login(loginForm)

            // Vérifiez si la réponse est valide et contient l'access token
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

    fun saveToDataStoreRepository(bearerToken: String, userId: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveBearerToken(bearerToken)
            dataStoreRepository.saveUserId(userId)
        }
    }
}