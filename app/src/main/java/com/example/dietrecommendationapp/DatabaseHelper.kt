package com.example.dietrecommendationapp


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Users"
        private const val COLUMN_ID = "Id"
        private const val COLUMN_NAME = "Name"
        private const val COLUMN_AGE = "Age"
        private const val COLUMN_WEIGHT = "Weight"
        private const val COLUMN_LIFESTYLE = "Lifestyle"
        private const val COLUMN_FOOD_PREFERENCE = "FoodPreference"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, $COLUMN_AGE INTEGER, $COLUMN_WEIGHT INTEGER, $COLUMN_FOOD_PREFERENCE TEXT, $COLUMN_LIFESTYLE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(name: String, age:Int, weight: Float, lifestyle:String, foodPreference:String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        values.put(COLUMN_WEIGHT, weight)
        values.put(COLUMN_LIFESTYLE, lifestyle)
        values.put(COLUMN_FOOD_PREFERENCE, foodPreference)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun getAllUsers(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}
