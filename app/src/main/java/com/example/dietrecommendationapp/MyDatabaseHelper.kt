package com.example.dietrecommendationapp
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.Nothing as Nothing1

class MyDatabaseHelper(context: Context,private val tableName: String) :
//You must declare tableName as a private val so the rest of your class can access it.


    SQLiteOpenHelper(context, "MyDatabase.db", null, 1) {
    //A database(a file) is created with the name-MyDatabase.db

        override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                    "CREATE TABLE IF NOT EXISTS $tableName (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "age INTEGER, " +
                            "gender TEXT, " +
                            "weight INTEGER, " +
                            "height INTEGER, " +
                            "waterIntake INTEGER, " +
                            "work-study-hours INTEGER, " +
                            "sleepTime INTEGER, " +
                            "weight INTEGER, " +
                            "height INTEGER)"
                    ) }
    //When the database(a file) is created, a table (table name same as that of user name) is also created
    //In this table, all data will be stored

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertData(book_title: String, book_author: String,book_description: String, book_genre: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("book_title", book_title)
            put("book_author", book_author)
            put("book_description", book_description)
            put("book_genre", book_genre)
        }
        val result = db.insert(tableName, null, values)
        return result != -1L
    }

    fun userByTitle(book_title : String): Pair<Boolean, Triple<String,String,String>?> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE book_title = ?", arrayOf(book_title))


        if (cursor != null && cursor.moveToFirst())
        {   val book_author = cursor.getString(cursor.getColumnIndexOrThrow("book_author"))
            val book_description = cursor.getString(cursor.getColumnIndexOrThrow("book_description"))
            val book_genre = cursor.getString(cursor.getColumnIndexOrThrow("book_genre"))

            val userData = Triple(book_author, book_description, book_genre)
            cursor.close()
            db.close()
            return Pair(true, userData)
        }


        cursor?.close()
        //? is used because cursor can also be null in this senario
        db.close()
        return Pair(false, null)
    }

    fun getAllData(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${tableName}", null)
        val buffer = StringBuffer()
        while (cursor.moveToNext()) {
            buffer.append("ID: ${cursor.getInt(0)}\n")
            buffer.append("Title: ${cursor.getString(1)}\n")
            buffer.append("Author: ${cursor.getString(2)}\n")
            buffer.append("Description: ${cursor.getString(3)}\n")
            buffer.append("Genre: ${cursor.getString(4)}\n\n")
        }
        cursor.close()
        return buffer.toString()
    }







}
