package com.example.fishnov.ui.pages.joinCompany

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityJoinCompanyBinding
import com.example.fishnov.ui.pages.profile.ProfileActivity
import org.json.JSONObject

class JoinCompanyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinCompanyBinding
    private lateinit var viewModel: JoinCompanyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[JoinCompanyViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[JoinCompanyViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_company)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    private fun setupViews() {

        binding.joinButton.setOnClickListener {

            if (binding.editTextToken.text.toString().isNotEmpty()) {

                val form = JSONObject()

                form.put("id", viewModel.callId())
                form.put("token_company", binding.editTextToken.text.toString())

                val result = viewModel.joinCompany(form)

                result.onSuccess { dataStore ->
                    viewModel.saveToDataStoreRepository(dataStore.bearerToken)
                    Toast.makeText(this@JoinCompanyActivity, "Company joined !", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@JoinCompanyActivity, ProfileActivity::class.java))
                }
                result.onFailure { error ->
                    Toast.makeText(this@JoinCompanyActivity, "Company not find !", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}