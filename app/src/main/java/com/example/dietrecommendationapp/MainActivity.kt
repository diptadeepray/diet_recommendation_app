//DialogBox is there
//Toast is there


package com.example.dietrecommendationapp


import DialogFragment
import ChoiceDialogFragment
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.DialogFragment

import com.example.dietrecommendationapp.databinding.ActivityMainBinding
import java.util.Calendar

import android.database.Cursor






class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val dbHelper = DatabaseHelper(this)

        //Data stays in the app, evern after the app restarts (Unlike Tuple, Map, etc.)
        //Dsts stored in key-value pairs
        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)






        binding.chooseTimeAndData.setOnClickListener()
        {
            // Get a calendar instance
            val cal = Calendar.getInstance()
            var c=""

            // Create a DatePickerDialog
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c=String.format("Date Chosen -- day: %d, month: %d, year: %d", dayOfMonth, monthOfYear, year) },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

            // Set the title and show the dialog
            datePicker.setTitle("Choose a Date")
            datePicker.show()
        }








        binding.previousDetails.setOnClickListener()
        {
            val cursor: Cursor = dbHelper.getAllUsers()
            val stringBuilder = StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(1)
                    val age = cursor.getString(2)
                    val weight= cursor.getInt(3)
                    val lifestyle = cursor.getString(4)
                    val foodPreference = cursor.getString(5)

                    stringBuilder.append("ID: $id\nName: $name\nAge: $age\nWeight: $weight\nLifestyle: $lifestyle\nFood Preference: $foodPreference\n\n")
                } while (cursor.moveToNext())
            } else {
                stringBuilder.append("No data found")
            }
            cursor.close()
            binding.previousDetails.text = stringBuilder.toString()
        }





        binding.choiceDialog.setOnClickListener()
        {
            val Dialog = ChoiceDialogFragment()
            Dialog.show(supportFragmentManager, "Dialog")
        }

        binding.submitButton.setOnClickListener {
            val name = binding.ageInput.text.toString()
            var age: Int = 0
            var weight: Float = 0.0f

            if (binding.ageInput.text.toString().toIntOrNull() != null) {
                val age = binding.ageInput.text.toString().toInt()
            } else if (binding.weightInput.text.toString().toFloatOrNull() != null) {
                val weight = binding.weightInput.text.toString().toFloat()
            } else {
                Toast.makeText(this, "Please enter age in Integer and Weight in Integer/Float", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }




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













            //Saving data to Shared Preference
            if (name == null || weight == null)
            { Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show() }
            else {
                val editor = sharedPref.edit()
                editor.putString(name, weight.toString())
                editor.apply() // Save changes
            }


                //Saving data to SQLite
                if  ((name.isNotEmpty() && dietaryPreference.isNotEmpty() && activityLevel.isNotEmpty()) && (!(age == null || weight == null))) {
                    val isInserted = dbHelper.insertUser(name, age, weight, activityLevel ,dietaryPreference)
                    if (isInserted) {
                        Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
                        binding.nameInput.text.clear()
                        binding.ageInput.text.clear()

                    } else {
                        Toast.makeText(this, "Error Saving Data", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                }


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
