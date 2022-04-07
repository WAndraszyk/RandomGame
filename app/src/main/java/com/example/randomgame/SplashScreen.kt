package com.example.randomgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val db = DBHelper(this@SplashScreen)

        Handler(Looper.getMainLooper()).postDelayed({
            if(db.isLoggedIn()) {
                val intent = Intent(this@SplashScreen, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                val intent= Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}