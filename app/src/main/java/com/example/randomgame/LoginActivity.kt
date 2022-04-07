package com.example.randomgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        val db = DBHelper(this@LoginActivity)

        val loginButton = findViewById<Button>(R.id.logIn)
        val signUpButton = findViewById<Button>(R.id.signUp)
        val nickField = findViewById<EditText>(R.id.nick)
        val passwordField = findViewById<EditText>(R.id.password)
        val rankingButton = findViewById<Button>(R.id.rankingButton)

        loginButton.setOnClickListener {
            val nick = nickField.text.toString()
            val password = passwordField.text.toString()
            if(nick.isNotEmpty() && password.isNotEmpty()) {
                if (db.logIn(nick, password)) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Błędne dane logowania!", Toast.LENGTH_SHORT)
                        .show()
                }
            }else{
                Toast.makeText(applicationContext, "Wpisz prawidłowy nick i hasło!", Toast.LENGTH_SHORT).show()
            }
        }

        signUpButton.setOnClickListener {
            val nick = nickField.text.toString()
            val password = passwordField.text.toString()
            if(nick.isNotEmpty() && password.isNotEmpty()){
                if(db.signUp(nick, password)){
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, "Użytkownik o takim nicku już istnieje!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext, "Wpisz prawidłowy nick i hasło!", Toast.LENGTH_SHORT).show()
            }
        }

        rankingButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RankingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val nickField = findViewById<EditText>(R.id.nick)
        val passwordField = findViewById<EditText>(R.id.password)
        nickField.text.clear()
        passwordField.text.clear()
        val db = DBHelper(this@LoginActivity)
        db.logOut()
    }

    override fun onStop() {
        super.onStop()
    }


}