package com.example.fishnov.ui.pages.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.databinding.ActivityProfileBinding
import com.example.fishnov.R
import com.example.fishnov.ui.pages.editProfile.EditProfileActvity
import com.example.fishnov.ui.pages.joinCompany.JoinCompanyActivity
import com.example.fishnov.ui.pages.joinCompany.JoinCompanyViewModel
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
        val resultUser = viewModel.getUserInfo()
        val jsonObjectUser = JSONObject(resultUser)
        if (jsonObjectUser["id_company"].equals(null)) {
            binding.companyButton.visibility = View.VISIBLE
            binding.company.visibility = View.GONE
        } else {
            val resultCompany = viewModel.getCompanyInfo(jsonObjectUser["id_company"].toString())
            val jsonObjectCompany = JSONObject(resultCompany)
            viewModel.company = jsonObjectCompany["name_company"].toString()
            binding.companyButton.visibility = View.GONE
            binding.company.visibility = View.VISIBLE
        }
        viewModel.email = jsonObjectUser["email"].toString()
        viewModel.firstName = jsonObjectUser["first_name"].toString()
        viewModel.lastName = jsonObjectUser["last_name"].toString()
        viewModel.registrationTrawler = jsonObjectUser["registration_trawler"].toString()

        binding.editButton.setOnClickListener() {
            startActivity(Intent(this@ProfileActivity, EditProfileActvity::class.java))
            finish()
        }

        binding.companyButton.setOnClickListener() {
            startActivity(Intent(this@ProfileActivity, JoinCompanyActivity::class.java))
            finish()
        }
    }
}