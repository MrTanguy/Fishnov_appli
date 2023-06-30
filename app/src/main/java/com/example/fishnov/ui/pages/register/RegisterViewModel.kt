package com.example.fishnov.ui.pages.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.classes.DataStoreObject
import com.example.fishnov.data.classes.RegisterForm
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val repository = API()
    private val context = getApplication<Application>()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    fun callAPIregister(registerForm: RegisterForm) : Result<DataStoreObject> = runBlocking {
        return@runBlocking try {

            saveToDataStoreRepository("", 0)

            val response = repository.register(registerForm)

            val answer = JSONObject(response)

            val accessToken = answer.optString("access_token")
            val id = answer.optInt("id")
            if (accessToken.isNotEmpty() and (id.toString() != "0")) {
                val dataStoreObject = DataStoreObject(accessToken, id)
                // Connexion réussie, DataStoreObject retourné
                Result.success(dataStoreObject)
            } else {
                val errorJson = JSONObject(response)
                val errors = errorJson.optJSONObject("errors")
                if (errors != null) {
                    val errorMap = mutableMapOf<String, List<String>>()
                    val keys = errors.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val errorArray = errors.optJSONArray(key)
                        val errorList = mutableListOf<String>()
                        if (errorArray != null) {
                            for (i in 0 until errorArray.length()) {
                                errorList.add(errorArray.optString(i))
                            }
                        }
                        errorMap[key] = errorList
                    }
                    Result.failure(Exception(errorMap.toString()))
                } else {
                    throw IOException(response)
                }
            }

        } catch (e: Exception) {
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