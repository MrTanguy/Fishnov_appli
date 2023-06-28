package com.example.fishnov.ui.pages.addFishing

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityAddFishingBinding
import com.example.fishnov.ui.pages.connected.ConnectedActivity
import com.google.gson.JsonObject
import org.json.JSONObject
import java.time.LocalDate

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
        addFishButton()
        save()
    }

    private fun addFish() {
        // Création du Spinner et de l'EditText
        val newSpinner = Spinner(this)

        val newEditText = EditText(this)
        newEditText.hint = "Quantity in Kg"
        newEditText.inputType = InputType.TYPE_CLASS_NUMBER

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

    private fun addFishButton() {
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

    private fun save() {
        val button = Button(this)
        button.id = View.generateViewId()
        button.text = "Save"

        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        button.layoutParams = layoutParams

        binding.constraintLayout.addView(button)

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)
        constraintSet.connect(button.id, ConstraintSet.BOTTOM, binding.constraintLayout.id, ConstraintSet.BOTTOM, 100)
        constraintSet.connect(button.id, ConstraintSet.START, binding.constraintLayout.id, ConstraintSet.START, 0)
        constraintSet.connect(button.id, ConstraintSet.END, binding.constraintLayout.id, ConstraintSet.END, 0)
        constraintSet.connect(binding.linearContainer.id, ConstraintSet.BOTTOM, button.id, ConstraintSet.BOTTOM, 100)
        constraintSet.applyTo(binding.constraintLayout)

        button.setOnClickListener {

            val result = JSONObject()

            // Ajout de la date
            result.put("date", LocalDate.now().toString())

            // Ajout des poissons
            for (i in 0 until count) {
                if (editTextList[i].text.isNotEmpty()) {
                    if (result.has(spinnerList[i].selectedItem.toString())) {
                        // result.put(spinnerList[i].selectedItem.toString(), result.get(spinnerList[i].selectedItem.toString()).asInt + editTextList[i].text.toString().toInt())
                        result.put(spinnerList[i].selectedItem.toString(), result.get(spinnerList[i].selectedItem.toString()))
                    } else {
                        result.put(spinnerList[i].selectedItem.toString(), editTextList[i].text.toString().toInt())
                    }
                }
            }

            if (result.length() > 1) {

                val response = viewModel.callAPIaddFishing(result)

                response.onSuccess {
                    Toast.makeText(this, "Fishing added", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(Intent(this, ConnectedActivity::class.java))
                }
                response.onFailure {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "You must add at least one fish", Toast.LENGTH_SHORT).show()
            }
        }
    }
}