package com.example.fishnov.data.repository

import android.util.Log
import com.example.fishnov.data.classes.RegisterForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class API () {

    private var client = OkHttpClient()

    suspend fun register(register: String) {
        return withContext(Dispatchers.IO) {
            val url = "http://10.0.2.2:80/api/login"

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            Log.d("TANGUY", "Requête éxécutée")
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body().toString()

                Log.d("respondeBody", responseBody)
            } catch (e: IOException) {
                Log.e("ERRORTANGUY", e.toString())
            }

        }
    }

}