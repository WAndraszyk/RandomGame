package com.example.randomgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        supportActionBar?.hide()
        val db = DBHelper(this@RankingActivity)

        val nicksView = findViewById<TextView>(R.id.nick1)
        val scoresView = findViewById<TextView>(R.id.wynik1)

        var scores = ""
        var nicks = ""
        val records = db.loadRanking()
        var i = 0
        val max = minOf(10, records[0].size)
        while(i < max){
            val player = records[0][i].toString()
            nicks = nicks + player + "\n"
            val score = records[1][i].toString()
            scores = scores + score + "\n"
            i += 1
        }
        nicksView.setText(nicks)
        scoresView.setText(scores)
    }
}