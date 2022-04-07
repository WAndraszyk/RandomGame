package com.example.randomgame

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val context = context
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "GameDB.db"
        //Table Players
        private const val TABLE_NAME = "Players"
        private const val COL_NICK = "Nick"
        private const val COL_PASS = "Password"
        private const val COL_LOGGED_IN = "Is_logged_in"
        private const val COL_SCORE = "Score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ($COL_NICK VARCHAR PRIMARY KEY, $COL_PASS VARCHAR, $COL_LOGGED_IN CHAR, $COL_SCORE INTEGER)")
        db!!.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val upgradeTableQuery = ("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL(upgradeTableQuery)
        onCreate(db)
    }

    fun signUp(nick:String, password:String): Boolean{
        val db: SQLiteDatabase = writableDatabase
        val query = ("SELECT * FROM $TABLE_NAME WHERE $COL_NICK='$nick'")
        val cursor = db.rawQuery(query,null)
        if (cursor.count>0){
            cursor.close()
            db.close()
            return false
        }
        val values: ContentValues = ContentValues()
        values.put(COL_NICK, nick)
        values.put(COL_PASS, password)
        values.put(COL_SCORE, 0)
        values.put(COL_LOGGED_IN, "Y")
        db.insert(TABLE_NAME, null, values)
        cursor.close()
        db.close()
        return true
    }

    fun logIn(nick:String, password:String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val query = ("SELECT * FROM $TABLE_NAME WHERE $COL_NICK='$nick' AND $COL_PASS='$password'")
        val cursor = db.rawQuery(query,null)
        if (cursor.count<=0){
            cursor.close()
            db.close()
            return false
        }
        val logInQuery = ("UPDATE $TABLE_NAME SET $COL_LOGGED_IN='Y' WHERE $COL_NICK='$nick'")
        db.execSQL(logInQuery)
        cursor.close()
        db.close()
        return true
    }

    fun isLoggedIn(): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val query = ("SELECT * FROM $TABLE_NAME WHERE $COL_LOGGED_IN='Y'")
        val cursor = db.rawQuery(query,null)
        if (cursor.count<=0){
            cursor.close()
            db.close()
            return false
        }
        cursor.close()
        db.close()
        return true
    }

    fun logOut(){
        if (isLoggedIn()){
            val db: SQLiteDatabase = writableDatabase
            val query = ("UPDATE $TABLE_NAME SET $COL_LOGGED_IN='N' WHERE $COL_LOGGED_IN='Y'")
            db.execSQL(query)
            db.close()
            Toast.makeText(context, "Wylogowano!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setScore(value: Int){
        val db: SQLiteDatabase = writableDatabase
        val query = ("UPDATE $TABLE_NAME SET $COL_SCORE=$COL_SCORE + $value WHERE $COL_LOGGED_IN='Y'")
        db.execSQL(query)
        db.close()
    }

    fun getScore(): Int{
        val db: SQLiteDatabase = writableDatabase
        val query = ("SELECT $COL_SCORE FROM $TABLE_NAME WHERE $COL_LOGGED_IN='Y'")
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    fun loadRanking(): Array<MutableList<out Any>> {
        val players = mutableListOf<String>()
        val scores = mutableListOf<Int>()
        val selectQuery = ("SELECT $COL_NICK, $COL_SCORE FROM $TABLE_NAME ORDER BY $COL_SCORE DESC LIMIT 10")
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do {
                val player = cursor.getString(0)
                val score = cursor.getInt(1)

                players.add(player)
                scores.add(score)
            } while (cursor.moveToNext())
        }
        db.close()
        return arrayOf(players, scores)
    }
}