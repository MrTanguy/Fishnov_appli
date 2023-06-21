package com.example.fishnov.ui.pages.editProfile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.classes.DataStoreObject
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    private val API = API()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    var firstName:String = ""
    var lastName:String = ""
    var registrationTrawler:String = ""

    fun callToken(): String = runBlocking {
        return@runBlocking dataStoreRepository.getBearerToken()
    }

    fun callId(): Int = runBlocking {
        return@runBlocking dataStoreRepository.getUserId()
    }

    fun getUserInfo(): String = runBlocking  {
        return@runBlocking API.get_user_info(callToken(), callId())
    }

    fun updateUserInfo(form: JSONObject): Result<DataStoreObject> = runBlocking  {
        return@runBlocking try {
            val response = API.update_user_info(callToken(), callId(), form)
            val answer = JSONObject(response)
            val accessToken = answer.optString("access_token")
            val id = answer.optInt("id")
            if (accessToken.isNotEmpty() and (id.toString() != "0")) {
                val dataStoreObject = DataStoreObject(accessToken, id)
                // Update réussie, DataStoreObject retourné
                Result.success(dataStoreObject)
            } else {
                // Erreur retournez une erreur avec le message approprié
                val errorMessage = answer.optString("message", "Error")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            // retournez l'exception
            Result.failure(e)
        }
    }

    fun saveToDataStoreRepository(bearerToken: String) {
        viewModelScope.launch {
            dataStoreRepository.saveBearerToken(bearerToken)
        }
    }

}