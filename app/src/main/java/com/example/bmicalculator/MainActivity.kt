package com.example.bmicalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.example.bmicalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etHeight.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.etWeight.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

        binding.btnCalculate.setOnClickListener {
            val weight = binding.etWeight.text.toString()
            val height = binding.etHeight.text.toString()

            if (validateInput(weight,height)){
                val weightInDouble = weight.toDouble()
                val heightInDouble = height.toDouble()

                val bmi = weightInDouble / ((heightInDouble/100) * (heightInDouble/100))

                val bmiIn2Digits = String.format("%.2f", bmi).toDouble()

                displayResult(bmiIn2Digits)
            }
        }
    }

    private fun validateInput(weight: String?, height: String?): Boolean{
        return when {
            weight.isNullOrEmpty() -> {
                Snackbar.make(binding.root, "Weight is empty", Snackbar.LENGTH_SHORT).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Snackbar.make(binding.root, "Height is empty", Snackbar.LENGTH_SHORT).show()
                return false
            }
            else -> { true}
        }
    }

    private fun displayResult(bmi: Double){
        val resultBmi = bmi.toString()
        binding.tvIndex.text = resultBmi
        binding.tvInfo.text = getText(R.string.normal_range)

        var color = 0

        when{
            bmi < 18.50 -> {
                binding.tvResult.text = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 -> {
                binding.tvResult.text = "Normal"
                color = R.color.normal
            }
            bmi in 25.00..29.99 -> {
                binding.tvResult.text = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99 -> {
                binding.tvResult.text = "Obese"
                color = R.color.obese
            }
        }
        binding.tvResult.setTextColor(ContextCompat.getColor(this, color))
    }
    private fun handleKeyEvent(view: View, keycode:Int):Boolean{
        if(keycode == KeyEvent.KEYCODE_ENTER){
            // Hide Keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
            return true
        }
        return false
    }
}