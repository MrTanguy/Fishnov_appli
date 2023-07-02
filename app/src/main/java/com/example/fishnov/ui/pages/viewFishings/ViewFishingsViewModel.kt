package com.example.fishnov.ui.pages.viewFishings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.runBlocking

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

    fun getAllFishings(): String = runBlocking {
        repository.getAllFishings(callToken(), callId())
    }
}