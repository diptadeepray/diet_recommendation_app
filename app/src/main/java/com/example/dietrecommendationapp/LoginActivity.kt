package com.example.dietrecommendationapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    lateinit var tableSpinner: Spinner
    lateinit var submitButton: Button
    lateinit var newUserText: TextView
    lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tableSpinner = findViewById(R.id.tableSpinner)
        submitButton = findViewById(R.id.submitButton)
        newUserText = findViewById(R.id.newUserText)

        db = openOrCreateDatabase("database.db", MODE_PRIVATE, null)

        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        val tableNames = mutableListOf<String>()

        while (cursor.moveToNext()) {
            tableNames.add(cursor.getString(0))
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tableNames)
        tableSpinner.adapter = adapter

        submitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newUserText.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}