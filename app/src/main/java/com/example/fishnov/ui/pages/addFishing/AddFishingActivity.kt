package com.example.fishnov.ui.pages.addFishing

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityAddFishingBinding

class AddFishingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFishingBinding
    private lateinit var viewModel: AddFishingViewModel

    var count = 0
    val spinnerList = mutableListOf<Spinner>()
    val editTextList = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_fishing)
        viewModel = ViewModelProvider(this)[AddFishingViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[AddFishingViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    private fun setupViews() {
        addFish()
        addSaveButton()
    }

    private fun addFish() {
        // Création du Spinner et de l'EditText
        val newSpinner = Spinner(this)
        val newEditText = EditText(this)

        // Ajout à la liste des Spinners et des EditText
        spinnerList.add(newSpinner)
        editTextList.add(newEditText)

        newSpinner.id = View.generateViewId()
        newEditText.id = View.generateViewId()

        val spinnerLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        if (count == 0) {
            spinnerLayoutParams.topToTop = ConstraintSet.PARENT_ID
        } else {
            spinnerLayoutParams.topToBottom = spinnerList[count - 1].id
            spinnerLayoutParams.topMargin = 100
        }

        newSpinner.layoutParams = spinnerLayoutParams


        val editTextLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        newEditText.layoutParams = editTextLayoutParams

        val options = arrayOf(
            "amande",
            "anchois",
            "araignée",
            "bar",
            "cabillaud",
            "congre",
            "coquilles st jaques",
            "crabe",
            "dorade royale",
            "hareng",
            "homard",
            "juliennes",
            "langouste",
            "lieu jaune",
            "lieu noir",
            "limande",
            "maigres",
            "maquereau",
            "merlan",
            "merlu",
            "mulet",
            "rouget",
            "sardine",
            "saumon",
            "sole",
            "thon blanc",
            "thon rouge",
            "turbot",
            "vernis",
            "vivaneau")

        val adapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        newSpinner.adapter = adapter

        binding.linearContainer.addView(newSpinner)
        binding.linearContainer.addView(newEditText)

        count++
    }

    fun addSaveButton() {
        val button = Button(this)
        button.id = View.generateViewId()
        button.text = "Add fish"

        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        button.layoutParams = layoutParams

        binding.constraintLayout.addView(button)

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)
        constraintSet.connect(button.id, ConstraintSet.BOTTOM, binding.constraintLayout.id, ConstraintSet.BOTTOM, 300)
        constraintSet.connect(button.id, ConstraintSet.START, binding.constraintLayout.id, ConstraintSet.START, 0)
        constraintSet.connect(button.id, ConstraintSet.END, binding.constraintLayout.id, ConstraintSet.END, 0)
        constraintSet.connect(binding.linearContainer.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.BOTTOM, 100)
        constraintSet.applyTo(binding.constraintLayout)



        button.setOnClickListener {
            addFish()
        }
    }
}