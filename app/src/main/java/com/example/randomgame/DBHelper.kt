package com.example.randomgame

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "GameDB.db"
        //Table
        private val TABLE_NAME = "Players"
        private val COL_NICK = "Nick"
        private val COL_PASS = "Password"
        private val COL_LOGED_IN = "Is loged in"
        private val COL_BEST_SCORE = "Key"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_NICK VARCHAR PRIMARY KEY, $COL_PASS VARCHAR, $COL_LOGED_IN CHAR, $COL_BEST_SCORE INTEGER)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }
}