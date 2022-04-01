package com.example.randomgame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newGameButton = findViewById<Button>(R.id.new_game_button)
        val restartButton = findViewById<Button>(R.id.restart_button)

        newGame()

        newGameButton.setOnClickListener {
            newGame()
        }

        restartButton.setOnClickListener {
            setScore(0)
            val scoreView = findViewById<TextView>(R.id.scoreView)
            scoreView.setText("Score: " + getScore().toString())
        }
    }

    private fun newGame() {
        generateAnswer()
        setGuesses(0)

        val guessButton = findViewById<Button>(R.id.guess_button)
        val guessNr = findViewById<TextView>(R.id.guessesView)
        val scoreView = findViewById<TextView>(R.id.scoreView)
        scoreView.setText("Score: " + getScore().toString())
        guessNr.setText("Guesses so far: " + getGuesses().toString())

        val builder = AlertDialog.Builder(this@MainActivity)
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
                when(game(getAnswer())){
                    0 -> {
                        Toast.makeText(applicationContext, "Twoja liczba jest mniejsza", Toast.LENGTH_LONG).show()
                        setGuesses(getGuesses()+1)
                    }
                    1 -> {
                        Toast.makeText(applicationContext, "Twoja liczba jest wieksza", Toast.LENGTH_LONG).show()
                        setGuesses(getGuesses()+1)
                    }
                    2 -> {
                        setGuesses(getGuesses()+1)
                        dialog.show()
                        when(getGuesses()){
                            1 -> setScore(getScore()+5)
                            in 2..4 -> setScore(getScore()+3)
                            in 5..6 -> setScore(getScore()+2)
                            in 7..10 -> setScore(getScore()+1)
                        }
                        generateAnswer()
                        setGuesses(0)
                        scoreView.setText("Score: " + getScore().toString())
                    }
                    -1 -> Toast.makeText(applicationContext, "Twoja liczba jest spoza zakresu 0-20!", Toast.LENGTH_LONG).show()
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

    private fun game(answer : Int): Int {
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

    private fun setScore(value: Int){
        val sharedScore = this.getSharedPreferences("com.example.randomgame.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("score", value)
        edit.apply()
    }

    private fun getScore(): Int {
        val sharedScore = this.getSharedPreferences("com.example.randomgame.shared", 0)
        return sharedScore.getInt("score", 0)
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
}