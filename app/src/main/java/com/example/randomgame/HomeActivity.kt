package com.example.randomgame

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        val newGameButton = findViewById<Button>(R.id.new_game_button)
        val rankingButton = findViewById<Button>(R.id.ranking_button_home)
        val logOutButton = findViewById<Button>(R.id.log_out_button)
        val db = DBHelper(this@HomeActivity)

        newGame()

        newGameButton.setOnClickListener {
            newGame()
        }

        rankingButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, RankingActivity::class.java)
            startActivity(intent)
        }

        logOutButton.setOnClickListener {
            db.logOut()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun newGame() {
        generateAnswer()
        setGuesses(0)
        game()
    }

    private fun game() {
        val db = DBHelper(this@HomeActivity)

        val guessButton = findViewById<Button>(R.id.guess_button)
        val guessNr = findViewById<TextView>(R.id.guessesView)
        val scoreView = findViewById<TextView>(R.id.scoreView)
        scoreView.setText("Score: " + db.getScore().toString())
        guessNr.setText("Guesses so far: " + getGuesses().toString())

        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setTitle("Wygrana")
        builder.setMessage("Gratulacje! Wygrales!")
        builder.setPositiveButton("OK"){ _: DialogInterface, _: Int ->}
        val dialog: AlertDialog = builder.create()

        builder.setTitle("Przegrana")
        builder.setMessage("Przykro mi. Przegrales.")
        builder.setPositiveButton("OK"){ _: DialogInterface, _: Int ->}
        val defeat: AlertDialog = builder.create()

        guessButton.setOnClickListener {
            if (getGuesses() < 10){
                when(makeGuess(getAnswer())){
                    0 -> {
                        Toast.makeText(applicationContext, "Twoja liczba jest mniejsza", Toast.LENGTH_SHORT).show()
                        setGuesses(getGuesses()+1)
                    }
                    1 -> {
                        Toast.makeText(applicationContext, "Twoja liczba jest wieksza", Toast.LENGTH_SHORT).show()
                        setGuesses(getGuesses()+1)
                    }
                    2 -> {
                        setGuesses(getGuesses()+1)
                        dialog.show()
                        when(getGuesses()){
                            1 -> db.setScore(5)
                            in 2..4 -> db.setScore(3)
                            in 5..6 -> db.setScore(2)
                            in 7..10 -> db.setScore(1)
                        }
                        generateAnswer()
                        setGuesses(0)
                        scoreView.setText("Score: " + db.getScore().toString())
                    }
                    -1 -> Toast.makeText(applicationContext, "Twoja liczba jest spoza zakresu 0-20!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                defeat.show()
                generateAnswer()
                setGuesses(0)
            }
            guessNr.setText("Guesses so far: " + getGuesses().toString())
        }
    }

    private fun makeGuess(answer : Int): Int {
        val guess = findViewById<EditText>(R.id.editTextNumber)
        if (guess.length() > 0 ) {
            val string = guess.text.toString()
            val x = try {
                string.toInt()
            }catch (exception: NumberFormatException){
                21
            }
            return if (x in 0..20) {
                if (x < answer) {
                    0
                } else if (x > answer) {
                    1
                } else if (x == answer) {
                    2
                } else {
                    -1
                }
            } else{
                -1
            }
        }
        else{
            return -1
        }
    }

    private fun setGuesses(value: Int){
        val sharedScore = this.getSharedPreferences("com.example.randomgame.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("guesses", value)
        edit.apply()
    }

    private fun getGuesses(): Int {
        val sharedScore = this.getSharedPreferences("com.example.randomgame.shared", 0)
        return sharedScore.getInt("guesses", 0)
    }

    private fun generateAnswer(){
        val sharedScore = this.getSharedPreferences("com.example.randomgame.shared",0)
        val edit = sharedScore.edit()
        val value = Random.nextInt(0,20)
        edit.putInt("answer", value)
        edit.apply()
    }

    private fun getAnswer(): Int {
        val sharedScore = this.getSharedPreferences("com.example.randomgame.shared", 0)
        return sharedScore.getInt("answer", 0)
    }

    override fun onRestart() {
        super.onRestart()
        game()
    }

    override fun onResume() {
        super.onResume()
        game()
    }
}