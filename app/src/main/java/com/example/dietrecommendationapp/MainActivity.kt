package com.example.dietrecommendationapp



import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietrecommendationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val age = binding.ageInput.text.toString().toIntOrNull()
            val weight = binding.weightInput.text.toString().toFloatOrNull()
            val activityLevel = binding.activitySpinner.selectedItem.toString()
            val dietaryPreference = when (binding.preferenceGroup.checkedRadioButtonId) {
                R.id.vegRadioButton -> "Vegetarian"
                R.id.nonVegRadioButton -> "Non-Vegetarian"
                R.id.veganRadioButton -> "Vegan"
                else -> "Unknown"
            }

            if (age == null || weight == null) {
                Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show()
            } else {
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