package com.example.guessgame_cc201013

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private val gameHandler = GameHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //restart the game
        restartGame.setOnClickListener {
            startNewGame()
        }

        val gameMode = gameHandler.startNewGame()
        updateGame(gameMode)

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                    val gameMode = gameHandler.play((letterView).text[0])
                    updateGame(gameMode)
                    letterView.background.setTint(Color.parseColor("#e8dbc0"))

                }
            }
        }
    }

    private fun updateGame(gameMode: GameMode) {
        when (gameMode) {
            is GameMode.Lost -> GameOver(gameMode.wordToGuess)
            is GameMode.Running -> {
                wordTextView.text = gameMode.underscoreWord
                //show faild attempts
                FalidTriesTextView.text = "Failed attempts: ${gameMode.currentlives}/6"
                //Log.d("NumberofTries" , gameState.currentTries.toString())
            }
            is GameMode.Won -> GameWon(gameMode.wordToGuess)
        }
    }


    private fun GameOver(wordToGuess: String) {
        wordTextView.text = wordToGuess.toUpperCase()
        FalidTriesTextView.text = "Failed attempts: 6/6"

        //create AlertDialog
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Game over")
            setMessage("Try next time !!")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                startNewGame()
            })
            setCancelable(false)
            //Display AlertDialog
            show()
        }

    }

    private fun GameWon(wordToGuess: String) {
        wordTextView.text = wordToGuess.toUpperCase()

        //create AlertDialog
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Congrats")
            setMessage("Go little rock star !! ")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                startNewGame()
            })
            //Display AlertDialog
            show()
        }
    }


    private fun startNewGame() {
        val gameMode = gameHandler.startNewGame()

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.background.setTint(Color.parseColor("#c4b085"))
            }
        }
        updateGame(gameMode)
    }
}