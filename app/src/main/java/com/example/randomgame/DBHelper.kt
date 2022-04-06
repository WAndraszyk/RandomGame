package com.example.randomgame

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "EDMTDB.db"
        //Table
        private val TABLE_NAME = "Message"
        private val COL_ID = "Id"
        private val COL_MESSAGE = "Info"
        private val COL_TYPE = "Type"
        private val COL_KEY = "Key"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_MESSAGE TEXT, $COL_TYPE TEXT, $COL_KEY TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }
}