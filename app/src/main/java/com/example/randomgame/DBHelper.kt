package com.example.randomgame

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "GameDB.db"
        //Table
        private const val TABLE_NAME = "Players"
        private const val COL_NICK = "Nick"
        private const val COL_PASS = "Password"
        private const val COL_LOGGED_IN = "Is_logged_in"
        private const val COL_BEST_SCORE = "Key"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ($COL_NICK VARCHAR PRIMARY KEY, $COL_PASS VARCHAR, $COL_LOGGED_IN CHAR, $COL_BEST_SCORE INTEGER)")
        db!!.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}