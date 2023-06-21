package com.example.fishnov.ui.pages.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.databinding.ActivityProfileBinding
import com.example.fishnov.R
import com.example.fishnov.ui.pages.editProfile.EditProfileActvity
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ProfileViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    private fun setupViews() {
        val result = viewModel.getUserInfo()
        val jsonObject = JSONObject(result)
        viewModel.email = jsonObject["email"].toString()
        viewModel.firstName = jsonObject["first_name"].toString()
        viewModel.lastName = jsonObject["last_name"].toString()
        viewModel.registrationTrawler = jsonObject["registration_trawler"].toString()

        binding.editButton.setOnClickListener() {
            startActivity(Intent(this@ProfileActivity, EditProfileActvity::class.java))
        }
    }
}