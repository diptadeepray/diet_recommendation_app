//DialogBox is there
//Toast is there


package com.example.dietrecommendationapp


import DialogFragment
import ChoiceDialogFragment
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.DialogFragment


import com.example.dietrecommendationapp.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        binding.chooseTimeAndData.setOnClickListener()
        {
            // Get a calendar instance
            val cal = Calendar.getInstance()
            var c="Hello"

            // Create a DatePickerDialog
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c=String.format("Date Chosen -- day: %d, month: %d, year: %d", dayOfMonth, monthOfYear, year) },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

            // Set the title and show the dialog
            datePicker.setTitle("Choose a Date")
            datePicker.show()
        }

        binding.submitButton2.setOnClickListener()
        {
            val Dialog = ChoiceDialogFragment()
            Dialog.show(supportFragmentManager, "Dialog")
        }

        binding.submitButton.setOnClickListener {
            val name = binding.ageInput.text.toString()
            val age = binding.ageInput.text.toString().toIntOrNull()
            val weight = binding.weightInput.text.toString().toFloatOrNull()
            val activityLevel = binding.activitySpinner.selectedItem.toString()
            val dietaryPreference = when (binding.preferenceGroup.checkedRadioButtonId)
            {
                R.id.vegRadioButton -> "Vegetarian"
                R.id.nonVegRadioButton -> "Non-Vegetarian"
                R.id.veganRadioButton -> "Vegan"
                else -> "Unknown"
            }

            val Dialog = DialogFragment()

            Dialog.show(supportFragmentManager, "Dialog")
            //It automatically cancels the dialog box,
            // unless explicitly mentioned the code with Dialog.setCancelable(false)

            Dialog.setCancelable(false)





            if (age == null || weight == null)
            {
                Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show()
            } else
            {
                val editor = sharedPref.edit()

                editor.putString(name, weight.toString())
                editor.apply() // Save changes


                val recommendation = getDietRecommendation(age, weight, activityLevel, dietaryPreference)
                binding.recommendationText.text = recommendation
            }
        }


    }

    private fun getDietRecommendation(age: Int, weight: Float, activityLevel: String, dietaryPreference: String): String {
        // Basic decision tree logic
        return when {
            dietaryPreference == "Vegan" -> "High protein vegan diet with lentils, tofu, and nuts."
            dietaryPreference == "Vegetarian" && activityLevel == "Active" -> "Balanced vegetarian diet with dairy, legumes, and whole grains."
            dietaryPreference == "Non-Vegetarian" && age < 30 -> "Lean protein diet with chicken, fish, and vegetables."
            dietaryPreference == "Non-Vegetarian" && age >= 30 -> "Low-carb diet with lean meat and leafy greens."
            else -> "Consult a nutritionist for a personalized plan."
        }
    }
}