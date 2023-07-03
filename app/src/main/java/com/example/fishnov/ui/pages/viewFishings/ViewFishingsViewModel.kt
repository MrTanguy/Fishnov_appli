package com.example.fishnov.ui.pages.viewFishings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ViewFishingsViewModel (application: Application) : AndroidViewModel(application) {

    val repository = API()
    private val context = getApplication<Application>()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    fun callToken(): String = runBlocking {
        return@runBlocking dataStoreRepository.getBearerToken()
    }

    fun callId(): Int = runBlocking {
        return@runBlocking dataStoreRepository.getUserId()
    }


    fun getAllFishings(): Result<JSONArray> = runBlocking {
        return@runBlocking try {
           val response =  repository.getAllFishings(callToken(), callId())

            val answer = JSONArray(response)

            if (answer.length() != 0) {
                Result.success(answer)
            } else {
                throw IOException("Nothing to display")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




}