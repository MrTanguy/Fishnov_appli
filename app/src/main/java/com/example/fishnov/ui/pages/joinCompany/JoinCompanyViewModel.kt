package com.example.fishnov.ui.pages.joinCompany

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

class JoinCompanyViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    private val API = API()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    fun callId(): Int = runBlocking {
        return@runBlocking dataStoreRepository.getUserId()
    }

    fun callToken(): String = runBlocking {
        return@runBlocking dataStoreRepository.getBearerToken()
    }
    fun saveToDataStoreRepository(bearerToken: String) {
        viewModelScope.launch {
            dataStoreRepository.saveBearerToken(bearerToken)
        }
    }

    fun joinCompany(form: JSONObject): Result<DataStoreObject> = runBlocking  {
        return@runBlocking try {

            Log.d("tanguy", "JoinCompanyViewModel : $form")

            val response = API.joinCompany(callToken(), form)
            val answer = JSONObject(response)
            val accessToken = answer.optString("access_token")
            val id = answer.optInt("id")
            Log.d("tanguy", "JoinCompanyViewModel : $answer")
            if (accessToken.isNotEmpty() and (id.toString() != "0")) {
                val dataStoreObject = DataStoreObject(accessToken, id)
                // Update réussie, DataStoreObject retourné
                Result.success(dataStoreObject)
            } else {
                val errorMessage = answer.optString("message", "Error")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}