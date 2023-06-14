package com.example.fishnov.ui.pages.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginViewModel(context: Context) : ViewModel() {

    private val repository = API()
    private val dataStoreRepository = DataStoreRepository(context)

    suspend fun callAPIlogin(loginForm: LoginForm): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.login(loginForm)

                // Vérifiez si la réponse est valide et contient l'access token
                val answer = JSONObject(response)
                val accessToken = answer.optString("access_token")
                if (accessToken.isNotEmpty()) {
                    // Connexion réussie, retournez l'access token
                    Result.success(accessToken)
                } else {
                    // Erreur de connexion, retournez une erreur avec le message approprié
                    val errorMessage = answer.optString("message", "Login failed")
                    Result.failure(Exception(errorMessage))
                }
            } catch (e: IOException) {
                // Erreur d'entrée/sortie, retournez une erreur avec le message approprié
                Result.failure(Exception("IO error: ${e.message}"))
            } catch (e: JSONException) {
                // Erreur de parsing JSON, retournez une erreur avec le message approprié
                Result.failure(Exception("JSON error: ${e.message}"))
            } catch (e: Exception) {
                // Autre erreur, retournez l'exception
                Result.failure(e)
            }
        }
    }

    fun saveBearerToken(bearerToken: String) {
        viewModelScope.launch {
            dataStoreRepository.saveBearerToken(bearerToken)
        }

    }
}