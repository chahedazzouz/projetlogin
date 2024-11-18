package com.example.tp4.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "users.db"
        const val DATABASE_VERSION = 1
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(name: String) {
        val db = writableDatabase
        val values = ContentValues().apply { put(COLUMN_NAME, name) }
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun getAllUsers(): List<String> {
        val userList = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(TABLE_USERS, arrayOf(COLUMN_NAME), null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                userList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun deleteUser(name: String) {
        val db = writableDatabase
        db.delete(TABLE_USERS, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
    }

    fun updateUser(oldName: String, newName: String) {
        val db = writableDatabase
        val values = ContentValues().apply { put(COLUMN_NAME, newName) }
        db.update(TABLE_USERS, values, "$COLUMN_NAME = ?", arrayOf(oldName))
        db.close()
    }
}
