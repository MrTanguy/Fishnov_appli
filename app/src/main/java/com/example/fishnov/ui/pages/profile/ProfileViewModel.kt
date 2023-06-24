package com.example.fishnov.ui.pages.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishnov.data.repository.API
import com.example.fishnov.data.repository.DataStoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    private val API = API()
    private val dataStoreRepository = DataStoreRepository.getInstance(context)

    var email: String = ""
    var firstName:String = ""
    var lastName:String = ""
    var registrationTrawler:String = ""
    var company:String = ""

    fun callToken(): String = runBlocking {
        return@runBlocking dataStoreRepository.getBearerToken()
    }

    fun callId(): Int = runBlocking {
        return@runBlocking dataStoreRepository.getUserId()
    }

    fun getUserInfo(): String = runBlocking  {
        return@runBlocking API.getUserInfo(callToken(), callId())
    }

    fun getCompanyInfo(id: String): String = runBlocking  {
        return@runBlocking API.getCompanyInfo(id, callToken())
    }
}