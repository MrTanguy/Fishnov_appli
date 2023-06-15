package com.example.fishnov.ui.pages.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var token: String = ""
    var id: String = ""

    private val context = getApplication<Application>()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    fun callToken(): String = runBlocking {
        return@runBlocking dataStoreRepository.getBearerToken()
    }

    fun callId(): Int = runBlocking {
        return@runBlocking dataStoreRepository.getUserId()
    }
}