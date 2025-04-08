package com.example.dietrecommendationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {


    private lateinit var db : MyDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        // Dummy registration screen logic

        var db = MyDatabaseHelper(this,"Hello")



    }
}