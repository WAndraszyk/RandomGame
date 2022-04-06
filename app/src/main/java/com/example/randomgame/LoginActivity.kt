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

        val loginButton = findViewById<Button>(R.id.logIn)
        val signUpButton = findViewById<Button>(R.id.signUp)
        val nickField = findViewById<EditText>(R.id.nick)
        val passwordField = findViewById<EditText>(R.id.password)
        val rankingButton = findViewById<Button>(R.id.rankingButton)

        loginButton.setOnClickListener {
            val nick = nickField.text.toString()
            val password = passwordField.text.toString()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val nick = nickField.text.toString()
            val password = passwordField.text.toString()
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        rankingButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RankingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(applicationContext, "LoginActivity on stop", Toast.LENGTH_LONG).show()
    }
}