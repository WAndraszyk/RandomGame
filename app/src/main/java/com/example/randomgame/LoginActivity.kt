package com.example.randomgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.logIn)
        val nickField = findViewById<EditText>(R.id.nick)
        val passwordField = findViewById<EditText>(R.id.password)

        loginButton.setOnClickListener {
            val nick = nickField.text.toString()
            val password = passwordField.text.toString()
        }
    }
}