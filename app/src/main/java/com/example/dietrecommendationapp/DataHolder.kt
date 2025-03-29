package com.example.dietrecommendationapp

//This different file is used so that the data
//can be used by other Kotlin classes also using...
//import com.example.readrover.DataHolder.myMap

import android.util.Log


object DataHolder {
    val myMap = mutableMapOf("admin" to "1234")


    fun addData(key: String, value: String) {
        myMap.put(key, value)
        //Log.d("MyTag", "\"Keys (Indices): ${myMap.indices.toList()}\"") // Recommended for debugging


        Log.d("MyTag","Values: $myMap") // Recommended for debugging

    }

    fun checkPassword(key: String, value: String): Any {
        for ((k, v) in myMap) {
            if (k == key && v==value) {
                println("Ok")
                return "Yes"
            }
        }
        return "No"
    }
}

